package com.github.freshchen.cn.cloud.web.config;


import com.github.freshchen.cn.cloud.web.filter.RequestWrapperReplaceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author freshchen
 * @since 2020/08/09
 **/
@Configuration(proxyBeanMethods = false)
@Slf4j
public class CustomRequestWrapperFilterConfiguration {

    public static final Integer REQUEST_WRAPPER_REPLACE_FILTER_ORDERED = Ordered.HIGHEST_PRECEDENCE;
    public static final String REQUEST_WRAPPER_REPLACE_FILTER_BEAN_NAME = "requestWrapperReplaceFilter";
    public static final String REQUEST_WRAPPER_REPLACE_FILTER_REGISTRATION_BEAN_NAME =
            "requestWrapperReplaceFilterRegistrationBean";

    @Bean(REQUEST_WRAPPER_REPLACE_FILTER_BEAN_NAME)
    public RequestWrapperReplaceFilter requestWrapperReplaceFilter() {
        return new RequestWrapperReplaceFilter();
    }

    @Bean(REQUEST_WRAPPER_REPLACE_FILTER_REGISTRATION_BEAN_NAME)
    public FilterRegistrationBean<RequestWrapperReplaceFilter> requestWrapperReplaceFilterRegistrationBean(
            RequestWrapperReplaceFilter requestWrapperReplaceFilter) {
        FilterRegistrationBean<RequestWrapperReplaceFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(requestWrapperReplaceFilter);
        registration.addUrlPatterns("/*");
        registration.setName(REQUEST_WRAPPER_REPLACE_FILTER_REGISTRATION_BEAN_NAME);
        registration.setOrder(REQUEST_WRAPPER_REPLACE_FILTER_ORDERED);
        log.info("all request will be wrapped as CustomRequestWrapper. filter order {}",
                REQUEST_WRAPPER_REPLACE_FILTER_ORDERED);
        return registration;
    }


}
