package com.esb.springbootHw.exception;

public class AgeNotEnoughException extends BaseException{
	
	private static final long serialVersionUID = 1L;

    public AgeNotEnoughException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public AgeNotEnoughException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }
}