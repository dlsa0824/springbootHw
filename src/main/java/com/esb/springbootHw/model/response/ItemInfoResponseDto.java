package com.esb.springbootHw.model.response;

import java.util.List;

import com.esb.springbootHw.entity.ItemInfo;

public class ItemInfoResponseDto {
	
	
	private List<ItemInfo> items;
	public List<ItemInfo> getItems() {
		return this.items;
	}
	
	public void setItems(List<ItemInfo> items) {
		this.items = items;
	}
}
