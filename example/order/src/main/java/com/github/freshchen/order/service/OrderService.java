package com.github.freshchen.order.service;

import com.github.freshchen.order.data.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

/**
 * @author darcy
 * @since 2022/09/23
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final Executor defaultExecutor;

    public void query(OrderDTO order) {
        if (order.getOrderNo().startsWith("TEST")) {
            test(order);
        }
        defaultExecutor.execute(() -> log.info("async {}", order));
    }

    @Trace(operationName = "test")
    private void test(OrderDTO order) {
        log.info("test env order {}", order);
    }


}
