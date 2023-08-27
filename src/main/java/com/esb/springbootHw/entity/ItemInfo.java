package com.esb.springbootHw.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.esb.springbootHw.validation.ValidationGroup;

public class ItemInfo {
	
	@NotNull(message = "item can not be null", groups = ValidationGroup.query.class)
	private String item;
	
//	@NotNull(message = "price can not be null", groups = ValidationGroup.query.class)
//	@Min(value = 0, message = "price can not under 0", groups = ValidationGroup.query.class)
	private Integer price;
	
//	@NotNull(message = "amount can not be null", groups = ValidationGroup.query.class)
//	@Min(value = 0, message = "can not be 0")
	private Integer amount;
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}