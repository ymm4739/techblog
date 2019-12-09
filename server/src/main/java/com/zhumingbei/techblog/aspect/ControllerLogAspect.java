package com.zhumingbei.techblog.aspect;

import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.constant.SessionConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
    @Pointcut("execution(public * com.zhumingbei.techblog.controller.*.*(..))")
    public void PointCut() {

    }


    @Around("PointCut()")
    public Object Around(ProceedingJoinPoint point) throws Throwable {
        /*
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpSession session = request.getSession();
        String token = request.getHeader(SessionConstant.HEADER_SESSION_TOKEN);
        if (token != null ) {
            if (!token.equals(session.getId())){
                return ApiResponse.of(50000, "Token失效");
            }else {
                UserBean user = (UserBean) session.getAttribute(SessionConstant.USER_INFO);
                if (user == null) {
                    return ApiResponse.of(50000, "Token非法");
                }

                UserDetails userDetails = CustomUserPrincipal.create(user, user.getEmail());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        */
        HashMap<String, Object> map = new HashMap<>();
        String className = point.getTarget().getClass().getName();
        map.put("className", className);
        String methodName = point.getSignature().getName();
        Long start = System.currentTimeMillis();
        Object result = point.proceed();
        Long end = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] args = point.getArgs();
        ArrayList<String> params = new ArrayList<>();
        for (Object arg : args) {
            params.add(arg.toString());
        }
        map.put("methodName", methodName);
        map.put("params", params);
        map.put("result", result);
        map.put("executionTime", end - start);
        map.put("date", format.format(new Date()));
        log.info("ControllerLogAspect:" + map);
        return result;
    }

    @Before("PointCut()")
    public void before(JoinPoint point) {

    }


}
