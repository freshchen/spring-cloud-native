package com.github.freshchen.cn.cloud.web.filter;

import com.github.freshchen.cn.cloud.web.wrapper.CustomRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author freshchen
 * @since 2021/11/26
 */
@Slf4j
public class RequestWrapperReplaceFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof CustomRequestWrapper)) {
            request = new CustomRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            ContentCachingResponseWrapper contentCachingResponse = (ContentCachingResponseWrapper) response;
            try {
                contentCachingResponse.copyBodyToResponse();
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("Failed to copy body to contentCachingResponse", e);
                }
            }
        }
    }
}
