package com.esb.springbootHw.repo;

import java.util.List;

import com.esb.springbootHw.entity.ItemInfo;

public interface Dao {
	public List<ItemInfo> query(String item);
	
	public List<ItemInfo> queryItems(List<ItemInfo> items);
	
	public List<ItemInfo> queryAll();
	
	public int insert(ItemInfo itemInfo);
	
	public int delete(ItemInfo itemInfo);
	
	public int[] batchInsert(List<ItemInfo> items);
	
	public int[] batchDelete(List<ItemInfo> items);
	
	public int[] batchUpdate(List<ItemInfo> items);

}