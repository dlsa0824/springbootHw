package com.esb.springbootHw.service.discount;

public class TotalDiscount implements Discount{

	@Override
	public int calculate(Object object) {

		int price = (int)object;
		double discount = 0.9;
		int discountBaseline = 100;
		
		if (price > discountBaseline) {
			return (int) (price * discount);
		} else {
			return price;
		}
	}
}
