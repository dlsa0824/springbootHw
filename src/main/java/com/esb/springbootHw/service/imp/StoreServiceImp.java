package com.esb.springbootHw.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.esb.springbootHw.constant.ResultCode;
import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.exception.ItemNotEnoughException;
import com.esb.springbootHw.repo.Dao;
import com.esb.springbootHw.service.StoreService;
import com.esb.springbootHw.service.discount.ItemDiscount;
import com.esb.springbootHw.service.discount.TotalDiscount;

@Service
public class StoreServiceImp implements StoreService{

	@Autowired
	public Dao storeDao;

	@Autowired
	public ItemDiscount itemDiscount;

	@Autowired
	public TotalDiscount totalDiscount;
	
	@Autowired
	public DefaultTransactionDefinition def;

	@Autowired
	public DataSourceTransactionManager transactionManager;

	public ItemInfo getItem(String item) throws ItemNotAvailableException {
		List<ItemInfo> itemList = storeDao.query(item);
		if (itemList.isEmpty()) {
			throw new ItemNotAvailableException(ResultCode.ITEM_NOT_AVAILABLE.getCode(), 
					item + " " + ResultCode.ITEM_NOT_AVAILABLE.getMessage());
		}
		return itemList.get(0);
	}
	
	public List<ItemInfo> getItems(List<ItemInfo> items) {
		return storeDao.queryItems(items);
	}
	
	public List<ItemInfo> getAllItems() {
		return storeDao.queryAll();
	}
	
	public int insertItem(ItemInfo itemInfo) throws DuplicateKeyException {
		return storeDao.insert(itemInfo);
	}
	
	public int deleteItem(ItemInfo itemInfo) {
		return storeDao.delete(itemInfo);
	}
	
	public int[] batchInsertItem (List<ItemInfo> items) {
		return storeDao.batchInsert(items);
	}
	
	public int[] batchDeleteItem(List<ItemInfo> items) {
		return storeDao.batchDelete(items);
	}
	
	public int[] batchUpdateItem(List<ItemInfo> items) {
		return storeDao.batchUpdate(items);
	}

	@Override
	public List<ItemInfo> getItemsInfo(List<ItemInfo> items) throws ItemNotAvailableException {
		List<ItemInfo> itemList = new ArrayList<>();
		if (items.size() > 0) {
			itemList = storeDao.queryItems(items);
			if (itemList.size() < items.size()) {
				for (ItemInfo item : items) {
					getItem(item.getItem());
				}
			}
		} else {
			itemList = getAllItems();
		}
		return itemList;
	}

	@Override
	@Transactional
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
	
	@Override
	@Transactional
	public int insertStock(ItemInfo itemInfo) {
		return insertItem(itemInfo);
	}

	@Override
	@Transactional
	public ResultMessage deleteStock(ItemInfo itemInfo) throws ItemNotAvailableException {
		int count = deleteItem(itemInfo);
		if (count == 0) {
			throw new ItemNotAvailableException(ResultCode.ITEM_NOT_AVAILABLE.getCode(),
					ResultCode.ITEM_NOT_AVAILABLE.getMessage());
		} else {
			return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), Arrays.asList(ResultCode.UPDATE_SUCCESS.getMessage()));
		}
	}

	@Override
	@Transactional
	public ResultMessage batchInsertStock(List<ItemInfo> items) { // 不如先select再去insert沒有的

		List<String> failedCases = new ArrayList<>();
		boolean batchRes;
		
		try {
			batchInsertItem(items);
			batchRes = true;
		} catch (DuplicateKeyException error) {
			batchRes = false;
		}

		if (!batchRes) { // 若batchInsert失敗，再一個一個insert
			for (ItemInfo itemInfo : items) {
				try {
					storeDao.insert(itemInfo);
				} catch (DuplicateKeyException innerError) {
					failedCases.add(itemInfo.getItem());
				}
			}
		}
		
		if (failedCases.isEmpty()) {
			return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), Arrays.asList(ResultCode.UPDATE_SUCCESS.getMessage()));
		} else if (failedCases.size() < items.size()) {
			return new ResultMessage(ResultCode.UPDATE_PART_SUCCESS.getCode(),
					Arrays.asList(ResultCode.UPDATE_PART_SUCCESS.getMessage() + ", fail case is: " + failedCases.toString()));
		} else {
			return new ResultMessage(ResultCode.ITEM_IS_AVAILABLE.getCode(), 
					Arrays.asList(ResultCode.ITEM_IS_AVAILABLE.getMessage() + ", fail case is: " + failedCases.toString()));
		}
	}

	@Override
	public ResultMessage batchUpdateStock(List<ItemInfo> items) throws ItemNotAvailableException {
		
		List<String> failedCases = new ArrayList<>();
		
		TransactionStatus status = transactionManager.getTransaction(def);
		int[] counts = batchUpdateItem(items);
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == 0) {
				failedCases.add(items.get(i).getItem());
			}
		}
		
		if (failedCases.isEmpty()) {
			transactionManager.commit(status);
			return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), Arrays.asList(ResultCode.UPDATE_SUCCESS.getMessage()));
		} else {
			transactionManager.rollback(status);
			throw new ItemNotAvailableException(ResultCode.ITEM_NOT_AVAILABLE.getCode(), ResultCode.ITEM_NOT_AVAILABLE.getMessage()
					+ ", fail case is: " + failedCases.toString());
		}
	}
	
	@Override
	public ResultMessage batchDeleteStock(List<ItemInfo> items) throws ItemNotAvailableException {
		
		List<String> failedCases = new ArrayList<>();
		
		TransactionStatus status = transactionManager.getTransaction(def);
		int[] counts = batchDeleteItem(items);
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == 0) {
				failedCases.add(items.get(i).getItem());
			}
		}
		
		if (failedCases.isEmpty()) {
			transactionManager.commit(status);
			return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), Arrays.asList(ResultCode.UPDATE_SUCCESS.getMessage()));
		} else {
			transactionManager.rollback(status);
			throw new ItemNotAvailableException(ResultCode.ITEM_NOT_AVAILABLE.getCode(), ResultCode.ITEM_NOT_AVAILABLE.getMessage()
					+ ", fail case is: " + failedCases.toString());
		}
	}
}