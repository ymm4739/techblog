package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.RoleBean;
import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.mapper.RoleMapper;
import com.zhumingbei.techblog.service.RoleService;
import com.zhumingbei.techblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    public RoleService roleService;
    @Autowired
    private AuthenticationManager authenticationManager;

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
        UserBean user = userService.findByEmail(email);
        if (user != null) {
            return ApiResponse.of(20002, "邮箱已注册");
        }
        user = new UserBean();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setEmail(email);

        userService.insert(user);
        int userID = user.getId();
        int roleID = roleService.findByRoleName("user").getId();
        userService.insertUserRole(userID, roleID);
        return ApiResponse.of(20000, "注册成功");
    }

    @PostMapping("/login")
    public ApiResponse login(String email, String password, Boolean isRememberMe, HttpServletRequest request) {
        UserBean user = userService.findByEmail(email);
        if (user != null) {
            String cryptPassword = user.getPassword();
            if (!new BCryptPasswordEncoder().matches(password, cryptPassword)) {
                return ApiResponse.of(20001, "密码错误");
            }
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
            if (isRememberMe) {
                session.setMaxInactiveInterval(SessionConstant.MAX_SESSION_SECOND);
            }
            log.info("user-sessionID: " + session.getId());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);


            return ApiResponse.of(20000, "登陆成功", user);
        }

        return ApiResponse.of(20001, "用户名或者密码错误");
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
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        return ApiResponse.of(20000, "退出登陆成功");
    }

    @GetMapping("/user/list")
    public List<UserBean> list() {
        log.info("user list");
        List<UserBean> userBeanList = userService.getList();
        return userBeanList;
    }

    @GetMapping("/user/random")
    public UserBean changePassword() {
        log.info("password");
        UserBean userBean = userService.getList().get(0);
        return userBean;
    }

    @GetMapping("/refreshAuthentication")
    public ApiResponse refresh() {
        return ApiResponse.ofSuccess("已刷新");
    }


    private Boolean verify(String s) {
        if (s == null) {
            return false;
        }
        return true;
    }
}
