package com.esb.springbootHw.service;

import java.util.List;

import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.exception.ItemNotAvailableException;

public interface StoreService {
	
	public List<ItemInfo> getItem(String object) throws ItemNotAvailableException ;
	public List<ItemInfo> getAllItemInfo();
	public int insertItem();
	public Object deleteItem();
	public Object batchInsertItem();
	public Object batchUpdateItem();
	public Object batchDeleteItem();
}
