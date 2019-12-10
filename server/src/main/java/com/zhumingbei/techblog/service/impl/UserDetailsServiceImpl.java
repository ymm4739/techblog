package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserBean user = userService.findByUsernameOrEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("该用户未注册");
        }
        return CustomUserPrincipal.create(user);
    }
}
