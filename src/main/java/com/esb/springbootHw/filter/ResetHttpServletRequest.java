/**
 * 程式資訊摘要：實作HttpServletRequestWrapper並覆寫getInputStream、getReader方法
 * 類別名稱：ResettableStreamHttpServletRequest.java
 * 程式內容說明：
 * 版本資訊：
 * 程式設計人員姓名：Sam,Chen
 * 程式修改記錄：2017年11月29日
 * 版權宣告：
 */
package com.esb.springbootHw.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author sam.xps
 *
 */
public class ResetHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] rawData;

    private HttpServletRequest request;

    private ResetServletInputStream servletStream;

    public ResetHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
        this.servletStream = new ResetServletInputStream();
    }

    /**
     * 重置Body Inputstream
     */
    public void reset() {
        try {
            if (servletStream.stream != null) {
                servletStream.stream.close();
            }
        } catch (IOException e) {
        }
        servletStream.stream = new ByteArrayInputStream(rawData);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null) {
            cloneInputStream();
            servletStream.stream = new ByteArrayInputStream(rawData);
        }
        return servletStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (rawData == null) {
            cloneInputStream();
            servletStream.stream = new ByteArrayInputStream(rawData);
        }
        return new BufferedReader(new InputStreamReader(servletStream, StandardCharsets.UTF_8));
    }

    private class ResetServletInputStream extends ServletInputStream {

        private InputStream stream;

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.ServletInputStream#isFinished()
         */
        @Override
        public boolean isFinished() {
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.ServletInputStream#isReady()
         */
        @Override
        public boolean isReady() {
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.ServletInputStream#setReadListener(javax.servlet.
         * ReadListener)
         */
        @Override
        public void setReadListener(ReadListener readListener) {
        }
    }

    private void cloneInputStream() throws IOException {
        InputStream is = request.getInputStream();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            byte[] binary = new byte[1024];
            int len = 0;
            while ((len = is.read(binary)) != -1) {
                bao.write(binary, 0, len);
            }
            rawData = bao.toByteArray();
        } finally {
            is.close();
            bao.close();
        }
    }
}
