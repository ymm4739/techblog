package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.Users;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@RestController
public class User {
    @Autowired
    public UserService userService;
    @PostMapping("/registry")
    public ApiResponse registry(String username, String email, String password){
        log.info(username);
        log.info( email);
        log.info(password);
        if(!verify(username)){
            return ApiResponse.of(400, "username is null");
        }
        if(!verify(email)){
            return ApiResponse.of(400, "email is null");
        }
        if(!verify(password)){
            return ApiResponse.of(400, "password is null");
        }
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userService.insert(user);
        return ApiResponse.of(200, "注册成功");
    }
    @PostMapping("/login")
    public ApiResponse login(String email, String password, HttpServletRequest request){
        log.info(email);
        log.info(password);
        Users user = userService.findByEmail(email, password);
        if (user != null){
            HttpSession session = request.getSession();
            if (!session.isNew()){
                session.invalidate();
                session = request.getSession(true);
            }

            HashMap<String, String> map = new HashMap<>();
            map.put("X-Token", session.getId());
            return ApiResponse.of(20000, "登陆成功", map);
        }
        return ApiResponse.of(20000, "用户名或者密码错误");
    }
    private Boolean verify(String s){
        if(s == null){
            return false;
        }
        return true;
    }
}
