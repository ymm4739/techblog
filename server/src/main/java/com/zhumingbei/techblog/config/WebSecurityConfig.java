package com.zhumingbei.techblog.config;

import com.zhumingbei.techblog.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private NonePermissionUrlConfig urlConfig;
    @Autowired
    private RbacAuthorityConfig rbacAuthorityConfig;
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .anyRequest().access("@rbacAuthorityConfig.hasPermission(request, authentication)")
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        //http.addFilterBefore(SpringSession, UsernamePasswordAuthenticationFilter.class);
        //http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity ignore = web.ignoring().and();

        urlConfig.getGet().forEach(url -> ignore.ignoring().antMatchers(HttpMethod.GET, url));
        urlConfig.getPost().forEach(url -> ignore.ignoring().antMatchers(HttpMethod.POST, url));
        urlConfig.getPattern().forEach(url -> ignore.ignoring().antMatchers(url));
    }


}
