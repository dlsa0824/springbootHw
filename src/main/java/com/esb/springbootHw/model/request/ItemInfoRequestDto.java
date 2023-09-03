package com.esb.springbootHw.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.validation.ValidationGroup;

public class ItemInfoRequestDto {
	
	@NotNull(message = "age can not be null")
	@Min(value = 10, message = "age under ten years old")
	private Integer age;
	
	@Valid
	@NotNull(message = "items can not be null")
	private List<ItemInfo> items;
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public List<ItemInfo> getItems() {
		return this.items;
	}
	
	public void setItems(List<ItemInfo> items) {
		this.items = items;
	}
}