package com.zhumingbei.techblog.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private Integer code;
    private String message;
    private Object data;

    public ApiResponse(StatusCode statusCode, Object data) {
        this(statusCode.getCode(), statusCode.getMessage(), data);
    }

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse of(int code, String message, Object data) {
        return new ApiResponse(code, message, data);
    }

    public static ApiResponse of(int code, String message) {
        return of(code, message, null);
    }

    public static ApiResponse ofSuccess(String message, Object data) {
        return of(20000, message, data);
    }
    public static ApiResponse ofSuccess(String message) {
        return ofSuccess(message, null);
    }
    public static ApiResponse ofSuccess(Object data) {
        return ofSuccess("操作成功", data);
    }
    public static ApiResponse ofSuccess() {return ofSuccess(null);}
}
