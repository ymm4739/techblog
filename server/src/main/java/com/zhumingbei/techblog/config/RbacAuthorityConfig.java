package com.zhumingbei.techblog.config;

import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.service.UserService;
import com.zhumingbei.techblog.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class RbacAuthorityConfig {
    @Autowired
    private UserService userService;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        principal = auth.getPrincipal();
        log.info("auth-principal: {}, context-principal: {}", authentication.getPrincipal(), principal);
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            UserBean user = userService.findByEmail(email);
            if (verify(request, user)) return true;
        } else {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher("/login");
            if (matcher.matches(request)) {
                return true;
            }
        }

        /*
        else {
            HttpSession session = request.getSession(false);
            if (session == null) {
                return false;
            }else {
                UserBean user = (UserBean) session.getAttribute(SessionConstant.USER_INFO);
                if (verify(request, user)) return true;
            }
        }

         */
        log.info("rbac permission fail");

        return false;


    }

    private boolean verify(HttpServletRequest request, UserBean user) {
        CustomUserPrincipal userPrincipal = CustomUserPrincipal.create(user, user.getEmail());
        //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        //authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("rbac permission urls: " + userPrincipal.getUrls());
        for (String url : userPrincipal.getUrls()) {
            AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(url);
            if (antPathRequestMatcher.matches(request)) {
                log.info("rbac permission success");

                return true;
            }
        }
        // throw new AccessDeniedException("权限不足，拒绝访问");
        return false;
    }
}
