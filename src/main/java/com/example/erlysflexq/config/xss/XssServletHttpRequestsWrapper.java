package com.example.erlysflexq.config.xss;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class XssServletHttpRequestsWrapper extends HttpServletRequestWrapper {
    public XssServletHttpRequestsWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value =  super.getHeader(name);
        if(!StrUtil.hasEmpty(value)){
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if(! StrUtil.hasEmpty(value)){
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        if(map != null){
            for (String key : map.keySet()) {
                String[] values = map.get(key);
                for (int i = 0; i < values.length; i++) {
                    if(! StrUtil.hasEmpty(values[i])){
                        values[i] = HtmlUtil.filter(values[i]);
                    }
                }
                map.put(key, values);
            }
        }
        return map;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values =  super.getParameterValues(name);

        if(values != null){
            for (int i = 0; i < values.length; i++) {
                if(! StrUtil.hasEmpty(values[i])){
                    values[i] = HtmlUtil.filter(values[i]);
                }
            }
        }
        return values;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        InputStream is = super.getInputStream();
        InputStreamReader in = new InputStreamReader(is);
        BufferedReader buffer = new BufferedReader(in);

        StringBuffer stringBuffer = new StringBuffer();
        String line = buffer.readLine();

        while (line != null){
            stringBuffer.append(line);
            line = buffer.readLine();
        }
        buffer.close();
        in.close();
        is.close();
        Map<String, Object> map = JSONUtil.parseObj(stringBuffer.toString());
        Map<String, Object> result = new LinkedHashMap<>();

        for (String key : map.keySet()) {
            Object value = map.get(key);
            if( value instanceof String){
                if(! StrUtil.hasEmpty(value.toString())){
                    value = HtmlUtil.filter(value.toString());
                    result.put(key, value);
                }
            }else{
                result.put(key, value);
            }
        }
        String json = JSONUtil.toJsonStr(result);
        ByteArrayInputStream bain = new ByteArrayInputStream(json.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bain.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}
