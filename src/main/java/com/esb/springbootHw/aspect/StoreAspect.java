//package com.esb.springbootHw.aspect;
//
//import java.util.Set;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.Validator;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.annotation.Validated;
//
//import com.esb.springbootHw.validation.ValidationGroup;
//
//@Aspect
//@Component
//public class StoreAspect {
//	
//	@Autowired
//	private Validator validator;
//
//    @Pointcut(value = "execution (public * com.esb.springbootHw.controller.*.*(..))")
//    public void storeAOP() {
//
//    }
//    
//    @Validated(ValidationGroup.query.class)
//    @Around("storeAOP()") 
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//    	Object[] args = joinPoint.getArgs();
//    	if (args == null) {
//    		return args;
//    	}
//    	for (Object arg : args) {
//    		if (arg == null) {
//    			continue;
//    		}
//    		Set<ConstraintViolation<Object>> validatedResult = validator.validate(arg);
//    		if (validatedResult.size() > 0) {
//    			throw new ConstraintViolationException(validatedResult);
//    		}
//    	}
//    	Object result = joinPoint.proceed();
//    	return result;
//    }
//}