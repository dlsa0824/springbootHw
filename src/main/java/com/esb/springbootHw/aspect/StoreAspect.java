package com.esb.springbootHw.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.esb.springbootHw.constant.ResultCode;
import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.model.request.ItemInfoRequestDto;

@Aspect
@Component
public class StoreAspect {

    @Pointcut(value = "execution (public * com.esb.springbootHw.controller.*.*(..))")
    public void storeAOP() {

    }
    
    @Around("storeAOP()") 
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    	Object[] args = joinPoint.getArgs();
    	for(Object arg : args) {
    		if (ItemInfoRequestDto.class.equals(arg.getClass())) {
    			ItemInfoRequestDto itemInfoRequestDto = (ItemInfoRequestDto) arg;
    			if (itemInfoRequestDto.getAge() < 10) {
    				ResultMessage errorMessage = new ResultMessage(ResultCode.AGE_NOT_REACH.getCode(), ResultCode.AGE_NOT_REACH.getMessage());
    				return errorMessage;
    			}
    		}
    	}
    	Object result = joinPoint.proceed();
    	return result;
    }
}
