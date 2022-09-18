package com.github.freshchen.cn.cloud.web.wrapper;

import com.google.common.collect.ObjectArrays;
import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author freshchen
 * @since 2022/1/19
 */
@Slf4j
public class CustomRequestWrapper extends HttpServletRequestWrapper {

    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private final Map<String, String> headerMap = new HashMap<>();

    private final byte[] body;

    private Map<String, String[]> parameterMap;

    public CustomRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = ByteStreams.toByteArray(request.getInputStream());


    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new RequestBodyCachingInputStream(body);
    }

    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        if (headerMap.containsKey(name)) {
            return headerMap.get(name);
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(headerMap.keySet());
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            values.add(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }

    @Override
    public String getParameter(String name) {
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(name);
        return values != null && values.length > 0 ? values[0] : null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (parameterMap == null) {
            Map<String, String[]> result = new LinkedHashMap<>();
            decode(getQueryString(), result);
            if (isFormPost()) {
                decode(getPostBodyAsString(), result);
            }
            parameterMap = Collections.unmodifiableMap(result);
        }
        return parameterMap;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Map<String, String[]> parameterMap = getParameterMap();
        return Collections.enumeration(parameterMap.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        Map<String, String[]> parameterMap = getParameterMap();
        return parameterMap.get(name);
    }

    private void decode(String queryString, Map<String, String[]> result) {
        if (queryString != null) {
            List<NameValuePair> inputParams = URLEncodedUtils.parse(queryString, StandardCharsets.UTF_8);
            for (NameValuePair e : inputParams) {
                String key = e.getName();
                String value = e.getValue();
                if (result.containsKey(key)) {
                    String[] newValue = ObjectArrays.concat(result.get(key), value);
                    result.remove(key);
                    result.put(key, newValue);
                } else {
                    result.put(key, new String[]{value});
                }
            }
        }
    }

    public String getPostBodyAsString() {
        return new String(body, StandardCharsets.UTF_8);
    }

    private boolean isFormPost() {
        String contentType = getContentType();
        return (contentType != null && contentType.contains(FORM_CONTENT_TYPE) &&
                HttpMethod.POST.matches(getMethod()));
    }

}
