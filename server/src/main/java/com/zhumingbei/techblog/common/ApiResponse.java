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

    public static ApiResponse ofSuccess(Object data) {
        return of(20000, "Success", data);
    }
}
