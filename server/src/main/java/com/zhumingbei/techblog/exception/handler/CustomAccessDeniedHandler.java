package com.zhumingbei.techblog.exception.handler;

import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.util.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        if (httpServletRequest.isRequestedSessionIdValid()) {
            ResponseUtil.renderJson(httpServletResponse, 40300, "权限不足，拒绝访问");
        }else {
            if (httpServletRequest.getHeader(SessionConstant.HEADER_SESSION_TOKEN) == null) {
                ResponseUtil.renderJson(httpServletResponse, 40100, "需要登陆，请立即登陆");
            }else {
                ResponseUtil.renderJson(httpServletResponse, 50000, "登陆令牌已失效，需要重新登陆");
            }
        }
    }
}
