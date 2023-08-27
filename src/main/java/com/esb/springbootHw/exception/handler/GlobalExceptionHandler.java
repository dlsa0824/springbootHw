package com.esb.springbootHw.exception.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.esb.springbootHw.constant.ResultCode;
import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.exception.AgeNotEnoughException;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.exception.ItemNotEnoughException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
	
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
	
	@ExceptionHandler
	public ResultMessage constraintViolationExceptionHandler(ConstraintViolationException error) {
		return new ResultMessage("1111", Arrays.asList(error.getMessage()));
	}
	
//	@ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResultMessage paramExceptionHandler(MethodArgumentNotValidException error) {
//        BindingResult exceptions = error.getBindingResult();
//        if (exceptions.hasErrors()) {
//            List<FieldError> fieldErrors = exceptions.getFieldErrors();
//            if (!fieldErrors.isEmpty()) {
//            	for (FieldError fieldError : fieldErrors) {
//            		System.out.println(fieldError.getDefaultMessage());
//            	}
//                return new ResultMessage(ResultCode.PARAMETER_ERROR.getCode(), ResultCode.PARAMETER_ERROR.getMessage());
//            }
//        }
//        return new ResultMessage(ResultCode.PARAMETER_ERROR.getCode(), ResultCode.PARAMETER_ERROR.getMessage());
//    }
	
	@ExceptionHandler
    public ResultMessage paramExceptionHandler(MethodArgumentNotValidException error) {
        List<FieldError> fieldErrors = error.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
        	List<String> errorInfo = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
            	errorInfo.add(fieldError.getDefaultMessage());
            }
            return new ResultMessage(ResultCode.PARAMETER_ERROR.getCode(), errorInfo);
        }
        return new ResultMessage(ResultCode.PARAMETER_ERROR.getCode(), Arrays.asList(ResultCode.PARAMETER_ERROR.getMessage()));
    }
	
	@ExceptionHandler
	public ResultMessage exceptionHandler(Exception error) {
		System.out.println(1);
		return new ResultMessage("9999" ,Arrays.asList(error.getMessage()));
		
	}
}
