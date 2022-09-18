package com.github.freshchen.order.controller;

import com.github.freshchen.cn.common.model.RestResponse;
import com.github.freshchen.order.data.OrderDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author freshchen
 * @since 2022/9/18
 */
@RestController
public class OrderController {

    @PostMapping("/order/query")
    public RestResponse<OrderDTO> query(@RequestBody OrderDTO order) {
        return RestResponse.success(order);
    }
}
