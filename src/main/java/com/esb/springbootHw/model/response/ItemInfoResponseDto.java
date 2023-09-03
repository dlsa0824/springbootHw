package com.esb.springbootHw.model.response;

import java.util.List;

import com.esb.springbootHw.entity.ItemInfo;

public class ItemInfoResponseDto {
	
	private String code;
	
	private List<ItemInfo> items;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public List<ItemInfo> getItems() {
		return this.items;
	}
	
	public void setItems(List<ItemInfo> items) {
		this.items = items;
	}
}
