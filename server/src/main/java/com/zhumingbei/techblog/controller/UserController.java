package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.bean.ArticleBean;
import com.zhumingbei.techblog.bean.PermissionBean;
import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.constant.BlogSiteConstant;
import com.zhumingbei.techblog.constant.SessionConstant;
import com.zhumingbei.techblog.service.*;
import com.zhumingbei.techblog.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RestController
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    public RoleService roleService;

    @Autowired
    private CustomUserPrincipal principal;
    @Autowired
    private FileUploadService fileUploadService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private ArticleService articleService;

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
        // userService.setPermission(initOwnedPermissions(userID, roleID));
        return ApiResponse.of(20000, "注册成功");
    }

    private List<PermissionBean> initOwnedPermissions(int userID, int roleID) {
        ArrayList permissions = new ArrayList();
        Set<String> urls = new HashSet<>();
        urls.add("/article/create");
        urls.add("/article/edit/*");
        urls.add("/article/delete/*");
        urls.add("/article/list");
        urls.add("/article/save/*");
        StringBuilder builder = new StringBuilder();
        for (String url: urls){
            builder.append("/user/" + userID);
            builder.append(url);
            permissions.add(new PermissionBean(userID, roleID, builder.toString()));
            builder.delete(0, builder.length());
        }
        return permissions;
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
            CustomUserPrincipal userPrincipal = principal.create(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());


            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("principal: {}", userPrincipal);
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
        List<UserBean> userBeanList = userService.getList();
        return userBeanList;
    }

    @PostMapping("/user/password/change")
    public ApiResponse changePassword(String username, String oldPassword, String newPassword ) {
        UserBean userBean = userService.findByUsername(username);
        if (userBean == null) {
            return ApiResponse.of(50001, "找不到用户");
        }
        String cryptPassword = userBean.getPassword();
        if (!bCryptPasswordEncoder.matches(oldPassword, cryptPassword)) {
            return ApiResponse.of(40000, "原密码错误");
        }
        userBean.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userService.update(userBean);
        return ApiResponse.ofSuccess("密码更改成功");
    }

    @PostMapping("/user/password/reset")
    public ApiResponse resetPassword(String email, String password, String verifyCode, String rawCode) {
        UserBean user = userService.findByEmail(email);
        if (user == null) {
            return ApiResponse.of(20003, "邮箱未注册");
        }
        String code = jwtUtil.getIDFromJWT(rawCode);
        if (verifyCode.equals(code)) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userService.update(user);
            return ApiResponse.ofSuccess("重置密码成功");
        }
        return ApiResponse.of(40000, "验证码错误，请重新获取验证码");

    }

    @GetMapping("/user/email/activate")
    public ApiResponse activateEmail(String token) {
        String email = jwtUtil.getIDFromJWT(token);
        if (email == null) {
            return ApiResponse.of(20003, "邮箱激活失败，请重新激活");
        }
        UserBean user = userService.findByEmail(email);
        if (user == null) {
            return ApiResponse.of(20003, "邮箱未注册，请注册之后再激活");
        }
        if (user.getIsValidEmail() == 1) {
            return ApiResponse.of(20003, "邮箱已被激活");
        }
        user.setIsValidEmail(1);
        userService.update(user);
        return ApiResponse.ofSuccess("邮箱激活成功");
    }

    @PostMapping("/user/sendActivatedEmail")
    public ApiResponse sendActivatedEmail(String email) {
        String jwt = jwtUtil.create(email);
        String subject = "邮箱激活";
        String content = "请点击下面的链接激活你的邮箱，" + BlogSiteConstant.DOMAIN + "/user/email/activate?token=" + jwt;
        return sendEmail(email, subject, content, "邮箱激活邮件已发送邮箱");
    }
    @PostMapping("/user/password/getVerifyCode")
    public ApiResponse sendVerifyCode(String email) {
        UserBean user = userService.findByEmail(email);
        if (user == null) {
            return ApiResponse.of(20003, "邮箱未注册");
        }

        int code = random(100000, 999999);
        String subject = "重置密码";
        String content = "随机验证码为" + code + ", 30分钟内有效";
        String jwt = jwtUtil.create("" + code);

        sendEmail(email, subject, content, "验证码邮件已发送请查收");
        HashMap<String, String> result = new HashMap<>();
        result.put("code", jwt);
        return ApiResponse.ofSuccess("验证码邮件已发送请查收", result);
    }

    @PostMapping("/user/avatar/change")
    public ApiResponse changeAvatar(MultipartFile file) {
        int userID = CustomUserPrincipal.getUserID();
        String result = fileUploadService.uploadAvatar(file, userID);
        if (result == null) {
            return ApiResponse.of(50004, "头像上传失败");
        }else if (result.isEmpty()){
            return ApiResponse.of(40000, "头像文件内容为空");
        }
        UserBean userBean = userService.findByID(userID);
        userBean.setAvatar(result);
        userService.update(userBean);
        return ApiResponse.ofSuccess("头像更换成功", result);
    }

    @PostMapping("/user/thumbs/article")
    public ApiResponse thumbs(int articleID, boolean addOne) {
        int userID = CustomUserPrincipal.getUserID();
        ArticleBean articleBean = articleService.findPublishedByID(articleID);
        if (articleBean == null) {
            return ApiResponse.of(40000, "文章不存在");
        }
        int likedNums = articleBean.getLikedNums();
        if (addOne == true) {
            articleBean.setLikedNums(likedNums + 1);
        }else articleBean.setLikedNums(likedNums - 1);
        articleService.update(articleBean);
        userService.thumbsArticle(userID, articleID, addOne);
        return addOne == true ? ApiResponse.ofSuccess("点赞成功") : ApiResponse.ofSuccess("取消点赞成功");
    }
    private ApiResponse sendEmail(String email, String subject, String content, String successfulMessage) {
        try {
            mailService.sendSimpleMail(email, subject, content, null);
        }catch (MessagingException e){
            log.error("发送激活邮件失败，{}", e.getMessage());
            return ApiResponse.of(50001, "系统发送邮件失败，请稍后重试");

        }
        return ApiResponse.ofSuccess(successfulMessage);
    }
    private int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
    private Boolean verify(String s) {
        if (s == null) {
            return false;
        }
        return true;
    }
}
