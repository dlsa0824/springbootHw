package com.esb.springbootHw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.esb.springbootHw.constant.ResultCode;
import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.exception.ItemNotEnoughException;
import com.esb.springbootHw.model.request.ItemInfoRequestDto;
import com.esb.springbootHw.service.StoreServiceImp;

@RestController
public class StoreController {
	
	@Autowired
	private StoreServiceImp storeServiceImp;

	@PostMapping("/getPrice")
	public Object getPrice(@RequestBody ItemInfoRequestDto itemInfoRequestDto) {
		List<ItemInfo> items = itemInfoRequestDto.getItems();
		try {
			items = storeServiceImp.getItemsInfo(items);
			return items;
		} catch (ItemNotAvailableException error) {
			return new ResultMessage(error.getCode(), error.getMessage());
		}
	}
	
	@PostMapping("/doPay")
	public Object doPay(@RequestBody ItemInfoRequestDto itemInfoRequestDto) {
		ArrayList<ItemInfo> items = itemInfoRequestDto.getItems();
		try {
			String total = storeServiceImp.payItems(items);
			Map<String, String> priceToPayMap= new HashMap<>();
			priceToPayMap.put("Price to Pay", total);
			return priceToPayMap;
		} catch (ItemNotAvailableException error) {
			return new ResultMessage(error.getCode(), error.getMessage());
		} catch(ItemNotEnoughException error) {
			return new ResultMessage(error.getCode(), error.getMessage());
		}
	}
	
	@PostMapping("/insertItem")
	public Object insertItem(@RequestBody ItemInfo itemInfo) {
		try {
			storeServiceImp.insertItem(itemInfo);
			return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), ResultCode.UPDATE_SUCCESS.getMessage());
		} catch (DuplicateKeyException Error) {
			return new ResultMessage(ResultCode.ITEM_IS_AVAILABLE.getCode(), ResultCode.ITEM_IS_AVAILABLE.getMessage());
		}
	}
	
	@PostMapping("/deleteItem")
	public Object deleteItem(@RequestBody ItemInfo itemInfo) {
		try {
			Object result = storeServiceImp.deleteItem(itemInfo);
			return result;
		} catch (ItemNotAvailableException error) {
			return new ResultMessage(error.getCode(), error.getMessage());
		}
	}
	
	@PostMapping("/batchInsertItem")
	public Object batchInsertItem(@RequestBody List<ItemInfo> items) {
		Object result = storeServiceImp.batchInsertItem(items);
		return result;
	}
	
	@PostMapping("/batchDeleteItem")
	public Object batchDeleteItem(@RequestBody List<ItemInfo> items){
		Object result = storeServiceImp.batchDeleteItem(items);
		return result;
	}
	
	@PostMapping("/batchUpdateItem")
	public Object batchUpdateItem(@RequestBody List<ItemInfo> items){
		Object result = storeServiceImp.batchUpdateItem(items);
		return result;
	}
}
