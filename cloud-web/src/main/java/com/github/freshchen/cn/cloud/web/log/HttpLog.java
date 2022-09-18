package com.github.freshchen.cn.cloud.web.log;

import com.github.freshchen.cn.cloud.web.wrapper.CustomRequestWrapper;
import com.github.freshchen.cn.common.util.LogUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author freshchen
 * @since 2021/11/26
 */
@Slf4j
public class HttpLog {
    private List<HttpLogSelector> httpLogSelectorList;

    @Value("#{'${nc.web.http.logger.allow.mime.type.list:}'.split(',')}")
    private String[] defaultAllowedMimeType;
    @Value("#{'${nc.web.http.logger.ignore.uri.list:}'.split(',')}")
    private String[] defaultIgnoredUrls;

    public HttpLog(List<HttpLogSelector> httpLogSelectorList) {
        this.httpLogSelectorList = httpLogSelectorList;
        if (this.httpLogSelectorList == null) {
            this.httpLogSelectorList = Lists.newArrayList();
        }
    }


    public void request(HttpServletRequest request) {
        if (request instanceof CustomRequestWrapper) {
            if (!allowLog(request)) {
                return;
            }
            Req.ReqBuilder logBuilder = Req.builder();
            logBuilder.method(request.getMethod());
            String url = getUrl(request);
            logBuilder.url(url);
            List<String> headerNames = Collections.list(request.getHeaderNames());
            logBuilder.headers(getHeaders(headerNames, request::getHeader));
            try {
                String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
                if (StringUtils.hasText(body)) {
                    logBuilder.body(LogUtils.trimWhiteAndBlankSpace(body));
                }
                log.info(logBuilder.build().toString());
            } catch (Exception e) {
                log.error(String.format("Failed to log response. request url: %s", url), e);
            }
        }
    }

    public void response(HttpServletRequest request, HttpServletResponse response, String cost) {
        if (response instanceof ContentCachingResponseWrapper) {
            if (!allowLog(request, response)) {
                return;
            }
            ContentCachingResponseWrapper contentCachingResponse = (ContentCachingResponseWrapper) response;
            Resp.RespBuilder logBuilder = Resp.builder();
            logBuilder.cost(cost);
            logBuilder.httpStatus(contentCachingResponse.getStatus());
            Collection<String> headers = contentCachingResponse.getHeaderNames();
            logBuilder.headers(getHeaders(headers, response::getHeader));
            try {
                String body = new String(contentCachingResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
                if (StringUtils.hasText(body)) {
                    logBuilder.body(LogUtils.trimWhiteAndBlankSpace(body));
                }
                log.info(logBuilder.build().toString());
            } catch (Exception e) {
                log.error("Failed to log response.", e);
            }
        }
    }

    private boolean allowLog(HttpServletRequest request, HttpServletResponse response) {
        boolean allowedUri = allowUri(request);
        boolean allowedMimeType = allowMimeType(response.getContentType());
        boolean allow = allowedUri && allowedMimeType;
        return allow;
    }

    private boolean allowLog(HttpServletRequest request) {
        boolean allowedUri = allowUri(request);
        boolean allowedMimeType = allowMimeType(request.getContentType());
        boolean allow = allowedUri && allowedMimeType;
        return allow;
    }

    private boolean allowUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        boolean allowedUri = true;
        for (String url : defaultIgnoredUrls) {
            if (null != uri && uri.equals(url)) {
                allowedUri = false;
            }
        }
        return allowedUri;
    }

    private boolean allowMimeType(String contentType) {
        boolean allowedMimeType = false;
        for (String mimeType : defaultAllowedMimeType) {
            if (null != contentType && contentType.contains(mimeType)) {
                allowedMimeType = true;
            }
        }
        return allowedMimeType;
    }

    private Collection<String> getHeaders(Collection<String> headerNames, Function<String, String> headerFunc) {
        return headerNames.stream().filter(this::filterHeader)
                .map(header -> LogUtils
                        .trimWhiteAndBlankSpace(String.format("%s:%s", header, headerFunc.apply(header))))
                .collect(Collectors.toList());
    }

    private boolean filterHeader(String headerName) {
        return httpLogSelectorList.stream().anyMatch(selector -> selector.selectHeader(headerName));
    }

    private String getUrl(HttpServletRequest request) {
        String query = request.getQueryString();
        String url = request.getRequestURL().toString();
        return query == null ? url : url + "?" + query;
    }

}
