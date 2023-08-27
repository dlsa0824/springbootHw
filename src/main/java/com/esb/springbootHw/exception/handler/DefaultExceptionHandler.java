package com.esb.springbootHw.exception.handler;

import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.esb.springbootHw.constant.ResultCode;
import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.exception.AgeNotEnoughException;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.exception.ItemNotEnoughException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestControllerAdvice
public class DefaultExceptionHandler {	
	
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
		return new ResultMessage("9999" ,Arrays.asList(error.getMessage()));
		
	}

}
