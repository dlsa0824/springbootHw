package com.esb.springbootHw.entity;

import java.util.List;

public class ResultMessage {
	
	private String code;
	private List<String> message;
	
	public ResultMessage(String code, List<String> message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<String> getMessage() {
		return this.message;
	}
	
	public void setMessage(List<String> message) {
		this.message = message;
	}
}