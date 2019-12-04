package com.zhumingbei.techblog.config;

import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@Configuration
public class SessionConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).excludePathPatterns("/login", "/registry");
    }

    @Bean
    public HeaderHttpSessionIdResolver httpSessionStrategy(){
        return new HeaderHttpSessionIdResolver(SessionConstant.HEADER_SESSION_TOKEN); //利用header 认证sessionID
    }

}
