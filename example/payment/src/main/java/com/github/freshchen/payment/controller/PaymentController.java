package com.github.freshchen.payment.controller;

import com.github.freshchen.cn.common.model.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author freshchen
 * @since 2022/9/18
 */
@RestController
@RequiredArgsConstructor
public class PaymentController {

    @GetMapping("/order/v1/query")
    public RestResponse<String> query() {
        return RestResponse.success("payment query");
    }
}
