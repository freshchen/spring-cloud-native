package com.github.freshchen.order.client;

import com.github.freshchen.cn.common.model.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author darcy
 * @since 2022/10/11
 **/
@FeignClient(value = "PaymentClient" ,url = "http://payment")
public interface PaymentClient {

    @GetMapping("/order/v1/query")
    RestResponse<String> query();
}
