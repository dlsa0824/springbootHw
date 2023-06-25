package com.esb.springbootHw.constant;

public enum ResultCode {
	
	UPDATE_SUCCESS        ("204", "update successfully"),
	UPDATE_PART_SUCCESS   ("205", "update partially successfully"),
	
	AGE_NOT_REACH         ("001", "age not reach 10"),
	ITEM_IS_AVAILABLE     ("002", "item is available"),
	ITEM_NOT_AVAILABLE    ("003", "item not available"),
	ITEM_NOT_ENOUGH       ("004", "item not enough");
	
	
	private String code;
	private String message;

	ResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
}
