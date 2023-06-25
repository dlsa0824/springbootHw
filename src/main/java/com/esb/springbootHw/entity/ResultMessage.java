package com.esb.springbootHw.entity;

public class ResultMessage {
	
	public String code;
	public String message;
	
	public ResultMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode( ) {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
