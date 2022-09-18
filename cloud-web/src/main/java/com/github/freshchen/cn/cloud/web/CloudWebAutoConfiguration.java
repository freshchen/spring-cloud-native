package com.github.freshchen.cn.cloud.web;

import com.github.freshchen.cn.cloud.web.config.CustomRequestWrapperFilterConfiguration;
import com.github.freshchen.cn.cloud.web.config.RequestLoggerFilterConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author freshchen
 * @since 2022/9/8
 */
@AutoConfiguration
@Import({
        CustomRequestWrapperFilterConfiguration.class,
        RequestLoggerFilterConfiguration.class
})
@PropertySource("classpath:cloud-web.properties")
public class CloudWebAutoConfiguration {
}
