package com.zhumingbei.techblog.interceptor;

import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.service.UserService;
import com.zhumingbei.techblog.service.impl.UserDetailsServiceImpl;
import com.zhumingbei.techblog.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
        if (!sessionID.equals(token)) {
            log.info("Token=" + token + ", sessionID=" + sessionID);
            ResponseUtil.renderJson(response, 50000, "Token无效，请重新登陆");
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*
        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/login");
        if (matcher.matches(request)) {

            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute(SessionConstant.USER_INFO);
            UserDetails userDetails = CustomUserPrincipal.create(user, user.getEmail());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("login session-principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal() );

        }
        */
    }
}
