package com.esb.springbootHw.filter;

//import lombok.AllArgsConstructor;
//import lombok.Data;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author he peng
 * @date 2018/9/11
 */
public class BodyHttpServletRequestWrapper extends HttpServletRequestWrapper {


    private byte[] body;
    private ServletInputStreamWrapper inputStreamWrapper;

    public BodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = IOUtils.toByteArray(request.getInputStream());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
        this.inputStreamWrapper = new ServletInputStreamWrapper(byteArrayInputStream);
        resetInputStream();
    }

    public void resetInputStream() {
//        this.inputStreamWrapper.setInputStream(new ByteArrayInputStream(this.body != null ? this.body : new byte[0]));
    	try {
            if (inputStreamWrapper.inputStream != null) {
            	inputStreamWrapper.inputStream.close();
            }
        } catch (IOException e) {
        }
        this.inputStreamWrapper.inputStream = new ByteArrayInputStream(this.body != null ? this.body : new byte[0]);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.inputStreamWrapper;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.inputStreamWrapper));
    }


//    @Data
//    @AllArgsConstructor
    private static class ServletInputStreamWrapper extends ServletInputStream {

        private InputStream inputStream;
        
        public ServletInputStreamWrapper(InputStream inputStream) {
        	this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }
    }
}
