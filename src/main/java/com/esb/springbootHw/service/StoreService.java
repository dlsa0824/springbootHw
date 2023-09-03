package com.esb.springbootHw.service;

import java.util.List;

import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.exception.ItemNotEnoughException;

public interface StoreService {
	
	public List<ItemInfo> getItemsInfo(List<ItemInfo> items) throws ItemNotAvailableException;
	
	public String payItems(List<ItemInfo> items) throws ItemNotAvailableException, ItemNotEnoughException;
	
	public int insertStock(ItemInfo itemInfo);
	
	public ResultMessage deleteStock(ItemInfo itemInfo) throws ItemNotAvailableException;
	
	public ResultMessage batchInsertStock(List<ItemInfo> items);
		
	public ResultMessage batchDeleteStock(List<ItemInfo> items) throws ItemNotAvailableException;

	ResultMessage batchUpdateStock(List<ItemInfo> items) throws ItemNotAvailableException;
	
}