package com.esb.springbootHw.exception;

public class ItemNotEnoughException extends BaseException{
	
	private static final long serialVersionUID = 1L;

    public ItemNotEnoughException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public ItemNotEnoughException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }
}