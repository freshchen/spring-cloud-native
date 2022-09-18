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
@Getter
@Builder
public class Resp {

    private Integer httpStatus;

    private String body;

    @Singular("header")
    private List<String> headers;

    private String cost;

    @Override
    public String toString() {
        String headerStr = StringUtils.EMPTY;
        if (!CollectionUtils.isEmpty(headers)) {
            headerStr = ", headers=" + headers;
        }
        return "Resp{" +
                "httpCode=" + httpStatus +
                ", body='" + body + '\'' +
                headerStr +
                ", cost='" + cost + '\'' +
                '}';
    }
}
