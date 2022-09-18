package com.github.freshchen.cn.cloud.web.config;


import com.github.freshchen.cn.cloud.web.filter.RequestLogFilter;
import com.github.freshchen.cn.cloud.web.log.HttpLogSelector;
import com.github.freshchen.cn.cloud.web.log.HttpLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * @author freshchen
 * @since 2020/08/09
 **/
@ConditionalOnProperty(name = "nc.web.request.logger.enabled", matchIfMissing = true)
@Configuration
@Slf4j
public class RequestLoggerFilterConfiguration {

    public static final Integer REQUEST_LOGGER_FILTER_ORDERED = Ordered.HIGHEST_PRECEDENCE + 10;
    public static final String REQUEST_LOGGER_FILTER_BEAN_NAME = "requestLoggerReplaceFilter";
    public static final String REQUEST_LOGGER_FILTER_REGISTRATION_BEAN_NAME =
            "requestLoggerFilterRegistrationBean";

    @Bean
    public HttpLog logWriter(@Autowired(required = false) List<HttpLogSelector> httpLogSelectorList) {
        return new HttpLog(httpLogSelectorList);
    }

    @Bean(REQUEST_LOGGER_FILTER_BEAN_NAME)
    public RequestLogFilter requestLogFilter(HttpLog httpLog) {
        return new RequestLogFilter(httpLog);
    }

    @Bean(REQUEST_LOGGER_FILTER_REGISTRATION_BEAN_NAME)
    public FilterRegistrationBean<RequestLogFilter> requestLoggerFilterRegistrationBean(
            RequestLogFilter requestLogFilter) {
        FilterRegistrationBean<RequestLogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(requestLogFilter);
        registration.addUrlPatterns("/*");
        registration.setName(REQUEST_LOGGER_FILTER_REGISTRATION_BEAN_NAME);
        registration.setOrder(REQUEST_LOGGER_FILTER_ORDERED);
        log.info("all request will be log. filter order {}", REQUEST_LOGGER_FILTER_ORDERED);
        return registration;
    }


}
