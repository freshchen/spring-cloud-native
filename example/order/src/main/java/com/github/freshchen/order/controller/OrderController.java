package com.github.freshchen.order.controller;

import com.github.freshchen.cn.common.model.RestResponse;
import com.github.freshchen.order.data.OrderDTO;
import com.github.freshchen.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunctions;

import javax.validation.Valid;

/**
 * @author freshchen
 * @since 2022/9/18
 */
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/v1/query")
    public RestResponse<OrderDTO> query(@Valid @RequestBody OrderDTO order) {
        orderService.query(order);
        return RestResponse.success(order);
    }
}
