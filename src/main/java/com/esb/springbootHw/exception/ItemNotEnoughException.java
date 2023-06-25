package com.esb.springbootHw.exception;

public class ItemNotEnoughException extends Exception{
	
	private static final long serialVersionUID = 1L;

    private String code;

    public ItemNotEnoughException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public ItemNotEnoughException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}