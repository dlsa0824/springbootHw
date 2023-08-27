package com.esb.springbootHw.exception;

public class ItemNotAvailableException extends BaseException{
	
	private static final long serialVersionUID = 1L;

    public ItemNotAvailableException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public ItemNotAvailableException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }
}