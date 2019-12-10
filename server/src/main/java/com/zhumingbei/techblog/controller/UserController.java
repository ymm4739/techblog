package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.service.RoleService;
import com.zhumingbei.techblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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
        user = userService.findByUsername(username);
        if (user != null) {
            return ApiResponse.of(20002, "用户名已被注册");
        }
        user = new UserBean();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);

        userService.insert(user);
        int userID = user.getId();
        int roleID = roleService.findByRoleName("user").getId();
        userService.insertUserRole(userID, roleID);
        return ApiResponse.of(20000, "注册成功");
    }

    @PostMapping("/login")
    public ApiResponse login( String loginName, String password, Boolean isRememberMe, HttpServletRequest request) {
        UserBean user = userService.findByUsernameOrEmail(loginName);
        if (user == null) {
            return ApiResponse.of(20001, "用户名错误");
        }
        if (user != null) {
            String cryptPassword = user.getPassword();
            if (!bCryptPasswordEncoder.matches(password, cryptPassword)) {
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
            if (isRememberMe) {
                session.setMaxInactiveInterval(SessionConstant.MAX_SESSION_SECOND);
            }
            log.debug("user-sessionID: " + session.getId());
            CustomUserPrincipal userPrincipal = CustomUserPrincipal.create(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());


            SecurityContextHolder.getContext().setAuthentication(authentication);


            return ApiResponse.of(20000, "登陆成功", user);
        }

        return ApiResponse.of(20001, "用户名或者密码错误");
    }

    @GetMapping("/user/info")
    public UserBean getInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserBean user = userService.findByToken(session.getId());
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

    @PostMapping("/user/password/change")
    public ApiResponse changePassword(String username, String oldPassword, String newPassword ) {
        UserBean userBean = userService.checkByUsernameAndPassword(username, bCryptPasswordEncoder.encode(oldPassword));
        if (userBean == null) {
            return ApiResponse.of(20002, "原密码错误");
        }
        userBean.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userService.update(userBean);
        return ApiResponse.ofSuccess("密码更改成功");
    }

    @GetMapping("/user/password/reset")
    public ApiResponse resetPassword() {

        return ApiResponse.ofSuccess("重置密码邮件已发送请查收");
    }

    @GetMapping("/user/email/activate")
    public ApiResponse activateEmail(String token) {
        return ApiResponse.ofSuccess("邮箱激活邮件已发送，请及时查收邮件激活");
    }

    private void actiateEmail(String email) {

    }
    private Boolean verify(String s) {
        if (s == null) {
            return false;
        }
        return true;
    }
}
