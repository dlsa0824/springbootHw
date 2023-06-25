package com.esb.springbootHw.model.request;

import java.util.ArrayList;

import com.esb.springbootHw.entity.ItemInfo;

public class ItemInfoRequestDto {
	
	public int age;
	public ArrayList<ItemInfo> items;
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public ArrayList<ItemInfo> getItems() {
		return this.items;
	}
	
	public void setItems(ArrayList<ItemInfo> items) {
		this.items = items;
	}

}
