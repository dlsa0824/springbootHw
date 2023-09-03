package com.esb.springbootHw.constant;

public enum ResultCode {
	
	UPDATE_SUCCESS        ("0000", "update successfully"),
	UPDATE_PART_SUCCESS   ("0001", "update partially successfully"),
	
	PARAMETER_ERROR       ("1001", "param is invalid"),
	ITEM_IS_AVAILABLE     ("1002", "item is available"),
	ITEM_NOT_AVAILABLE    ("1003", "item not available"),
	ITEM_NOT_ENOUGH       ("1004", "item not enough");
	
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