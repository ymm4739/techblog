package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.Users;
import com.zhumingbei.techblog.mapper.UserMapper;
import com.zhumingbei.techblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserMapper userMapper;
    @Override
    public int insert(Users user) {
        return userMapper.insert(user);
    }
}
