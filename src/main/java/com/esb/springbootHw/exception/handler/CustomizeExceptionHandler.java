package com.esb.springbootHw.exception.handler;

import java.util.Arrays;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.exception.AgeNotEnoughException;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.exception.ItemNotEnoughException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomizeExceptionHandler {
	
	@ExceptionHandler
	public ResultMessage ageNotEnoughExceptionHandler(AgeNotEnoughException error) {
		return new ResultMessage(error.getCode(), Arrays.asList(error.getMessage()));
	}
	
	@ExceptionHandler
	public ResultMessage itemNotAvailableExceptionHandler(ItemNotAvailableException error) {
		return new ResultMessage(error.getCode(), Arrays.asList(error.getMessage()));
	}
	
	@ExceptionHandler
	public ResultMessage itemNotEnoughException(ItemNotEnoughException error) {
		return new ResultMessage(error.getCode(), Arrays.asList(error.getMessage()));
	}
}
