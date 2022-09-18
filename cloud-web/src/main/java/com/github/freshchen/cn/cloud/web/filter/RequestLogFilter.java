package com.github.freshchen.cn.cloud.web.filter;

import com.github.freshchen.cn.cloud.web.log.HttpLog;
import com.github.freshchen.cn.common.util.TimeIntervalContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author freshchen
 * @since 2021/11/26
 */
public class RequestLogFilter extends OncePerRequestFilter {

    private static final String TOTAL = "total";

    private static final String ROOT_URL = "/";

    private final HttpLog httpLog;

    public RequestLogFilter(HttpLog httpLog) {
        this.httpLog = httpLog;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {
        boolean shouldSkip = ROOT_URL.equals(request.getRequestURI());
        if (shouldSkip) {
            filterChain.doFilter(request, response);
            return;
        }
        TimeIntervalContextHolder.start(TOTAL);
        httpLog.request(request);
        try {
            filterChain.doFilter(request, response);
            TimeIntervalContextHolder.stop(TOTAL);
            httpLog.response(request, response, TimeIntervalContextHolder.costString());
        } finally {
            TimeIntervalContextHolder.clear();
        }

    }
}
