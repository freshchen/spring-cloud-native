package com.github.freshchen.cn.cloud.web.log;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author freshchen
 * @since 2022/7/11
 */
@Builder
@Getter
public class Req {

    private String method;

    private String url;

    private String body;

    @Singular("header")
    private List<String> headers;

    @Override
    public String toString() {
        String bodyStr = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(body)) {
            bodyStr = ", body='" + body + '\'';
        }
        String headerStr = StringUtils.EMPTY;
        if (!CollectionUtils.isEmpty(headers)) {
            headerStr = ", headers=" + headers;
        }
        return "Req{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                bodyStr +
                headerStr +
                '}';
    }
}
