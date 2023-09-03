package com.esb.springbootHw.controlleradvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
public class GlobalExceptionHandler {
	
	@ExceptionHandler({AgeNotEnoughException.class})
	public ResultMessage ageNotEnoughExceptionHandler(AgeNotEnoughException error) {
		return new ResultMessage(error.getCode(), Arrays.asList(error.getMessage()));
	}
	
	@ExceptionHandler({ItemNotAvailableException.class})
	public ResultMessage itemNotAvailableExceptionHandler(ItemNotAvailableException error) {
		return new ResultMessage(error.getCode(), Arrays.asList(error.getMessage()));
	}
	
	@ExceptionHandler({ItemNotEnoughException.class})
	public ResultMessage itemNotEnoughException(ItemNotEnoughException error) {
		return new ResultMessage(error.getCode(), Arrays.asList(error.getMessage()));
	}
	
	// AOP Error
	@ExceptionHandler({ConstraintViolationException.class})
	public ResultMessage constraintViolationExceptionHandler(ConstraintViolationException error) {
		Set<ConstraintViolation<?>> errorResult = error.getConstraintViolations();
		List<String> errorMessages = new ArrayList<>();
		for (ConstraintViolation<?> errorObj : errorResult) {
			errorMessages.add(errorObj.getMessage());
		}
		return new ResultMessage(ResultCode.PARAMETER_ERROR.getCode(), errorMessages);
	}
	
	// Controller @Valid error
	@ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultMessage paramExceptionHandler(MethodArgumentNotValidException error) {
        List<FieldError> fieldErrors = error.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
        	List<String> errorMessages = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
            	errorMessages.add(fieldError.getDefaultMessage());
            }
            return new ResultMessage(ResultCode.PARAMETER_ERROR.getCode(), errorMessages);
        }
        return new ResultMessage(ResultCode.PARAMETER_ERROR.getCode(), Arrays.asList(ResultCode.PARAMETER_ERROR.getMessage()));
    }
	
	@ExceptionHandler({Exception.class})
	public ResultMessage exceptionHandler(Exception error) {
		return new ResultMessage("9999" ,Arrays.asList(error.getMessage()));
		
	}
}
