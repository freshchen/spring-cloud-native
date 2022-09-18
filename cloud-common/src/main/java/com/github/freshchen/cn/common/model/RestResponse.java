package com.github.freshchen.cn.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.github.freshchen.cn.common.constants.CommonConstants.HTTP_ERROR_SHOW_MESSAGE;
import static com.github.freshchen.cn.common.constants.CommonConstants.HTTP_SUCCESS_MESSAGE;


/**
 * @author freshchen
 * @since 2021/11/25
 */
@Data
@ApiModel("统一响应模型")
@NoArgsConstructor
public class RestResponse<T> {

    @ApiModelProperty(value = "是否成功", required = true)
    private Boolean success;

    @ApiModelProperty(value = "状态码", required = true)
    private Integer code;

    @ApiModelProperty(value = "状态码描述信息", required = true)
    private String msg;

    @ApiModelProperty("响应数据")
    private T data;

    public static <T> RestResponse<T> success() {
        return success(null);
    }

    public static <T> RestResponse<T> success(T data) {
        return success(200, HTTP_SUCCESS_MESSAGE, data);
    }

    public static <T> RestResponse<T> success(Integer code, String msg, T data) {
        RestResponse<T> response = of(code, msg, data);
        response.setSuccess(true);
        return response;
    }

    public static <T> RestResponse<T> error() {
        return error(500, HTTP_ERROR_SHOW_MESSAGE);
    }

    public static <T> RestResponse<T> error(Integer code, String msg) {
        RestResponse<T> response = of(code, msg, null);
        response.setSuccess(false);
        return response;
    }

    public static <T> RestResponse<T> error(Integer code, String msg, T data) {
        RestResponse<T> response = of(code, msg, data);
        response.setSuccess(false);
        return response;
    }

    private static <T> RestResponse<T> of(Integer code, String msg, T data) {
        RestResponse<T> response = new RestResponse<>();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

}
