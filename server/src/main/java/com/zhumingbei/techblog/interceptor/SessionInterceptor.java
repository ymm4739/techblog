package com.zhumingbei.techblog.interceptor;

import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String sessionID = session.getId();
        String token = request.getHeader(SessionConstant.HEADER_SESSION_TOKEN);
        if(!sessionID.equals(token)){
            log.info("Token=" + token + ", sessionID=" + sessionID);
            ResponseUtil.renderJson(response, 50000, "Token无效，请重新登陆");
            return false;
        }
        return true;
    }

}
