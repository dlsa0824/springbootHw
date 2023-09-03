package com.esb.springbootHw.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.esb.springbootHw.validation.ValidationGroup;

public class ItemInfo {

	@NotNull(message = "item can not be null", groups = {ValidationGroup.query.class, ValidationGroup.pay.class, ValidationGroup.update.class})
	@NotBlank(message = "item can not be blank", groups = {ValidationGroup.query.class, ValidationGroup.pay.class, ValidationGroup.update.class})
	private String item;

	@NotNull(message = "price can not be null", groups = {ValidationGroup.update.class})
	@Min(value = 1, message = "price must more than 0", groups = {ValidationGroup.update.class})
	private Integer price;

	@NotNull(message = "amount can not be null", groups = {ValidationGroup.pay.class, ValidationGroup.update.class})
	@Min(value = 1, message = "amount must more than 0", groups = {ValidationGroup.pay.class, ValidationGroup.update.class})
	private Integer amount;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}