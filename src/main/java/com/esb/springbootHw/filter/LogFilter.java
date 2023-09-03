package com.esb.springbootHw.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esb.springbootHw.constant.ConstParams;
import com.esb.springbootHw.entity.InboundInfo;
import com.esb.springbootHw.model.response.mask.ResponseMask;
import com.esb.springbootHw.utils.LoggerUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LogFilter extends OncePerRequestFilter implements ConstParams{
	
	private static final Logger inboundLogger = LogManager.getLogger("InboundLog");
	private static final Logger apLogger = LogManager.getLogger("ApLog");
	private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final Random random = new Random();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		BodyHttpServletRequestWrapper requestWrapper = new BodyHttpServletRequestWrapper((HttpServletRequest) request);
		BodyHttpServletResponseWrapper responseWrapper = new BodyHttpServletResponseWrapper((HttpServletResponse) response);
		long startTime = System.currentTimeMillis();
				
		filterChain.doFilter(requestWrapper, responseWrapper);
		requestWrapper.resetInputStream();
		long endTime = System.currentTimeMillis();
		long costTime = endTime - startTime;
		try {
			recordInboundLog(requestWrapper, responseWrapper, costTime);
		} catch(Exception exception) {
			apLogger.error(LoggerUtils.stacktraceString(exception));
		}
		
		responseWrapper.flushBuffer();
	}
	
	private void recordInboundLog(BodyHttpServletRequestWrapper request, BodyHttpServletResponseWrapper response, long costTime) throws IOException {
		
		InboundInfo inboundInfo = new InboundInfo();
		
	    Date now = new Date();
	    System.out.println(sdfDate.format(now));
		
		UUID uuid = UUID.randomUUID();
		
		ThreadContext.put(UU_ID, uuid.toString());
		ThreadContext.put(MSG_NO, SENDER_CODE + "_" + sdfDate.format(now) + "_" + String.format("%03d", random.nextInt(1000)));
		
		inboundInfo.setQueryUrl(request.getRequestURI());
		try {
			inboundInfo.setRequest(objectMapper.readValue(new String(request.getBody()),Map.class));
		} catch (Exception exception) {
			apLogger.warn(LoggerUtils.stacktraceString(exception));
		}
		try {
			inboundInfo.setResponse(objectMapper.readValue(new String(response.getBody()), ResponseMask.class));
		} catch (Exception exception) {
			apLogger.warn(LoggerUtils.stacktraceString(exception));
		}
		inboundInfo.setStatus(String.valueOf(response.getStatus()));
		inboundInfo.setCostTime(costTime);
		
		inboundLogger.info(objectMapper.writeValueAsString(inboundInfo));
		
		ThreadContext.clearMap();
		
	}
}
