package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/registry")
    public ApiResponse registry(String username, String email, String password) {
        if (!verify(username)) {
            return ApiResponse.of(40000, "username is null");
        }
        if (!verify(email)) {
            return ApiResponse.of(40000, "email is null");
        }
        if (!verify(password)) {
            return ApiResponse.of(40000, "password is null");
        }
        UserBean user = new UserBean();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        userService.insert(user);
        return ApiResponse.of(20000, "注册成功");
    }

    @PostMapping("/login")
    public ApiResponse login(String email, String password, Boolean isRememberMe, HttpServletRequest request) {
        UserBean user = userService.findByEmail(email, password);
        if (user != null) {
            HttpSession session = request.getSession();
            UserBean old = userService.findByToken(session.getId());
            //different user login the same client
            if (old != null && !old.getEmail().equals(user.getEmail())) {
                session.invalidate();
                session = request.getSession(true);
            }
            user.setToken(session.getId());
            if (isRememberMe != true) user.setIsRememberMe(0);
            else user.setIsRememberMe(1);
            userService.update(user);
            session.setAttribute(SessionConstant.USER_INFO, user);
            session.setMaxInactiveInterval(SessionConstant.DEFAULT_SESSION_SECOND);
            if (isRememberMe){
                session.setMaxInactiveInterval(SessionConstant.MAX_SESSION_SECOND);
            }
            return ApiResponse.of(20000, "登陆成功", user);
        }

        return ApiResponse.of(20001,"用户名或者密码错误");
}

    @GetMapping("/user/info")
    public UserBean getInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute(SessionConstant.USER_INFO);
        return user;
    }

    @PostMapping("/logout")
    public ApiResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null ){
            session.invalidate();
        }
        return ApiResponse.of(20000, "退出登陆成功");
    }

    private Boolean verify(String s) {
        if (s == null) {
            return false;
        }
        return true;
    }
}
