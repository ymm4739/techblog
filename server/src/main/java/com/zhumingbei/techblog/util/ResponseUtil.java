package com.zhumingbei.techblog.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zhumingbei.techblog.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class ResponseUtil {
    public static void renderJson(HttpServletResponse response, int code, String message, Object data){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(JSONUtil.toJsonStr(new JSONObject(ApiResponse.of(code, message, data))));
        }catch (IOException e){
            log.error("Response render json error, ", e);
        }
    }
    public static void renderJson(HttpServletResponse response, int code, String message){
        renderJson(response, code, message, null);
    }
}
