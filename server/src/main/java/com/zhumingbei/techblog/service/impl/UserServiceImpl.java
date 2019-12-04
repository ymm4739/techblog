package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.mapper.UserMapper;
import com.zhumingbei.techblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserMapper userMapper;
    @Override
    public int insert(UserBean user) {
        return userMapper.insert(user);
    }

    @Override
    public UserBean findByEmail(String email, String password) {
        return userMapper.checkUser(email, password);
    }

    @Override
    public void update(UserBean user) {
        userMapper.update(user);
    }

    @Override
    public UserBean findByToken(String token) {
        return userMapper.selectByToken(token);
    }
}
