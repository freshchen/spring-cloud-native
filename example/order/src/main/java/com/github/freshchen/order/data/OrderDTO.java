package com.github.freshchen.order.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author freshchen
 * @since 2022/9/18
 */
@Data
public class OrderDTO {

    @NotBlank
    private String orderNo;
}
