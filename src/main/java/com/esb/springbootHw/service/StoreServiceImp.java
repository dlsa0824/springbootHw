package com.esb.springbootHw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.esb.springbootHw.constant.ResultCode;
import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.exception.ItemNotEnoughException;
import com.esb.springbootHw.repository.StoreDao;
import com.esb.springbootHw.service.discount.ItemDiscount;
import com.esb.springbootHw.service.discount.TotalDiscount;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StoreServiceImp {

	@Autowired
	private StoreDao storeDao;

	private ItemDiscount itemDiscount = new ItemDiscount();

	private TotalDiscount totalDiscount = new TotalDiscount();

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TransactionTemplate transactionTemplate;

	public ItemInfo getItem(String item) throws ItemNotAvailableException {
		List<ItemInfo> itemList = storeDao.query(item);
		if (itemList.isEmpty()) {
			throw new ItemNotAvailableException(ResultCode.ITEM_NOT_AVAILABLE.getCode(), 
					item + " " + ResultCode.ITEM_NOT_AVAILABLE.getMessage());
		}
		return itemList.get(0);
	}

	public List<ItemInfo> getAllItemInfo() {
		List<ItemInfo> itemList = storeDao.queryAll();
		return itemList;
	}

	public List<ItemInfo> getItemsInfo(List<ItemInfo> items) throws ItemNotAvailableException {
		List<ItemInfo> itemList = new ArrayList<>();
		if (items.size() > 0) {
			for (ItemInfo itemInfo : items) {
				itemList.add(getItem(itemInfo.getItem()));
			}
		} else {
			itemList = getAllItemInfo();
		}
		return itemList;
	}

	public String payItems(List<ItemInfo> items) throws ItemNotAvailableException, ItemNotEnoughException {
		int total = 0;
		List<ItemInfo> remainingItems = new ArrayList<>();
		for (ItemInfo itemInfo : items) {
			ItemInfo stockItem = getItem(itemInfo.getItem());
			if (itemInfo.getAmount() > stockItem.getAmount()) {
				throw new ItemNotEnoughException(ResultCode.ITEM_NOT_ENOUGH.getCode(),
						itemInfo.getItem() + " " + ResultCode.ITEM_NOT_ENOUGH.getMessage());
			}
			itemInfo.setPrice(stockItem.getPrice());

			total += itemDiscount.calculate(itemInfo);

			stockItem.setAmount(stockItem.getAmount() - itemInfo.getAmount());
			remainingItems.add(stockItem);
		}
		batchUpdateItem(remainingItems);

		total = totalDiscount.calculate(total);
		return String.valueOf(total);
	}

	public int insertItem(ItemInfo itemInfo) {
		return storeDao.insert(itemInfo);
	}

	public Object deleteItem(ItemInfo itemInfo) throws ItemNotAvailableException {
		int count = storeDao.delete(itemInfo);
		if (count == 0) {
			throw new ItemNotAvailableException(ResultCode.ITEM_NOT_AVAILABLE.getCode(),
					ResultCode.ITEM_NOT_AVAILABLE.getMessage());
		} else {
			return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), ResultCode.UPDATE_SUCCESS.getMessage());
		}
	}

	public Object batchInsertItem(List<ItemInfo> items) {

		List<String> failedCases = new ArrayList<>();

		boolean batchRes = transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus transactionStatus) {
				try {
					storeDao.batchInsert(items);
					return true;
				} catch (DuplicateKeyException error) {
					transactionStatus.setRollbackOnly();
					return false;
				}
			}
		});

		if (!batchRes) {
			for (ItemInfo itemInfo : items) {
				try {
					storeDao.insert(itemInfo);
				} catch (DuplicateKeyException innerError) {
					failedCases.add(itemInfo.getItem());
				}
			}
		}

		if (failedCases.isEmpty()) {
			return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), ResultCode.UPDATE_SUCCESS.getMessage());
		} else if (failedCases.size() < items.size()) {
			return new ResultMessage(ResultCode.UPDATE_PART_SUCCESS.getCode(),
					ResultCode.UPDATE_PART_SUCCESS.getMessage() + ", fail case is: " + failedCases.toString());
		} else {
			return new ResultMessage(ResultCode.ITEM_IS_AVAILABLE.getCode(), ResultCode.ITEM_IS_AVAILABLE.getMessage() 
					+ ", fail case is: " + failedCases.toString());
		}
	}

	public Object batchUpdateItem(List<ItemInfo> items) {
		Object result = transactionTemplate.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus transactionStatus) {
				int[] counts = storeDao.batchUpdate(items);
				for (int i = 0; i < counts.length; i++) {
					if (counts[i] == 0) {
						transactionStatus.setRollbackOnly();
						return new ResultMessage(ResultCode.ITEM_NOT_AVAILABLE.getCode(),
								items.get(i).getItem() + " " + ResultCode.ITEM_NOT_AVAILABLE.getMessage());
					}
				}
				return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), ResultCode.UPDATE_SUCCESS.getMessage());
			}
		});
		return result;
	}

	public Object batchDeleteItem(List<ItemInfo> items) {
		Object result = transactionTemplate.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus transactionStatus) {
				int[] counts = storeDao.batchDelete(items);
				for (int i = 0; i < counts.length; i++) {
					if (counts[i] == 0) {
						transactionStatus.setRollbackOnly();
						return new ResultMessage(ResultCode.ITEM_NOT_AVAILABLE.getCode(),
								items.get(i).getItem() + " " + ResultCode.ITEM_NOT_AVAILABLE.getMessage());
					}
				}
				return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), ResultCode.UPDATE_SUCCESS.getMessage());
			}
		});
		return result;
	}
}
