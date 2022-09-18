package com.github.freshchen.cn.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author freshchen
 * @since 2021/11/25
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonConstants {

    public static final Integer CORE = Math.max(Runtime.getRuntime().availableProcessors(), 1);
    public static final String UNKNOWN = "unknown";
    public static final String REDIS = "redis";
    public static final String MYSQL = "mysql";

    public static final String XXL_JOB = "xxl-job";
    public static final String KAFKA = "kafka";
    public static final String JMS = "jms";
    public static final String MONGODB = "mongodb";
    public static final String VAULT = "vault";
    public static final String TRUE = "true";
    public static final String HTTP_SUCCESS_MESSAGE = "ok";
    public static final String HTTP_ERROR_SHOW_MESSAGE = "The server is busy";

}
