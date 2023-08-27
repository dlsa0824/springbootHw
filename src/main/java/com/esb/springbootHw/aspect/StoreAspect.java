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
//
//import com.esb.springbootHw.constant.ResultCode;
//import com.esb.springbootHw.exception.AgeNotEnoughException;
//import com.esb.springbootHw.model.request.ItemInfoRequestDto;
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
////    @Around("storeAOP()") 
////    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
////    	Object[] args = joinPoint.getArgs();
////    	for(Object arg : args) {
////    		if (ItemInfoRequestDto.class.equals(arg.getClass())) {
////    			ItemInfoRequestDto itemInfoRequestDto = (ItemInfoRequestDto) arg;
////    			if (itemInfoRequestDto.getAge() < 10) {
////    				System.out.println("1");
////    				throw new AgeNotEnoughException(ResultCode.AGE_NOT_REACH.getCode(), ResultCode.AGE_NOT_REACH.getMessage());
////    			}
////    		}
////    	}
////    	Object result = joinPoint.proceed();
////    	return result;
////    }
//    
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
//    		Set<ConstraintViolation<Object>> result = validator.validate(arg);
//    		if (result.size() > 0) {
//    			System.out.println("result");
//    			ConstraintViolation<Object> v = result.iterator().next();
//    			String message = v.getMessage();
//    			throw new ConstraintViolationException(message, result);
//    		}
//    	}
//    	Object result = joinPoint.proceed();
//    	return result;
//    }
//}