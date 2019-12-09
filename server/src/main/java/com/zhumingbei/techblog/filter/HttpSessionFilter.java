package com.zhumingbei.techblog.filter;

import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Slf4j
public class HttpSessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AntPathRequestMatcher matcher1 = new AntPathRequestMatcher("/login");
        AntPathRequestMatcher matcher2 = new AntPathRequestMatcher("/registry");
        if (matcher1.matches(request) || matcher2.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession();
        String sessionID = session.getId();
        String token = request.getHeader(SessionConstant.HEADER_SESSION_TOKEN);
        if (!sessionID.equals(token)) {
            log.info("Token=" + token + ", sessionID=" + sessionID);
            ResponseUtil.renderJson(response, 50000, "Token无效，请重新登陆");
            return;
        }
        UserBean user = (UserBean) session.getAttribute(SessionConstant.USER_INFO);
        if (user == null) {
            ResponseUtil.renderJson(response, 20003, "根据token找不到用户");
            return;
        }

        UserDetails userDetails = CustomUserPrincipal.create(user, user.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
