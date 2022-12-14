package com.github.freshchen.order.controller;

import com.github.freshchen.cn.common.model.RestResponse;
import com.github.freshchen.order.client.PaymentClient;
import com.github.freshchen.order.data.OrderDTO;
import com.github.freshchen.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author freshchen
 * @since 2022/9/18
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final PaymentClient paymentClient;

    @PostMapping("/order/v1/query")
    public RestResponse<OrderDTO> query(@Valid @RequestBody OrderDTO order) {
        orderService.query(order);
        log.info("client {}", paymentClient);
        paymentClient.query();
        return RestResponse.success(order);
    }
}
