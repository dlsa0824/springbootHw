package com.esb.springbootHw.service.discount;

import org.springframework.stereotype.Component;

import com.esb.springbootHw.entity.ItemInfo;

@Component
public class ItemDiscount implements Discount{
	
	@Override
	public int calculate(Object object) {
		
		ItemInfo itemInfo = (ItemInfo)object;
		
		int total = 0;
		double discount = 0.8;
		String item = itemInfo.getItem();
		int price = itemInfo.getPrice();
		int amount = itemInfo.getAmount();
				
		switch(item) {
			case "milk": // 兩件8折
			    total = (int) (price * 2 * itemPairSet(amount) * discount + price * (amount - 2 * itemPairSet(amount)));
				break;
			case "coffee": // 第二件-10元
				total = price * amount - itemPairSet(amount) * 10;
				break;
			default :
				total = price * amount;
		}
		return total;
	}
	
	private int itemPairSet(int amount) {
		return amount / 2;
	}
}