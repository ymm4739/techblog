package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.PermissionBean;
import com.zhumingbei.techblog.bean.RoleBean;
import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.mapper.PermissionMapper;
import com.zhumingbei.techblog.mapper.RoleMapper;
import com.zhumingbei.techblog.mapper.UserMapper;
import com.zhumingbei.techblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public RoleMapper roleMapper;
    @Autowired
    public PermissionMapper permissionMapper;
    @Override
    public int insert(UserBean user) {
        return userMapper.insert(user);
    }

    @Override
    public UserBean findByUsernameOrEmail(String s) {
        UserBean user = userMapper.selectByUsernameOrEmail(s);
        if (user == null) {
            return null;
        }

        return user;
    }

    @Override
    public void update(UserBean user) {
        userMapper.update(user);
    }

    @Override
    public UserBean findByToken(String token) {
        return userMapper.selectByToken(token);
    }

    @Override
    public UserBean checkByEmailAndPassword(String email, String password) {
        return userMapper.checkByEmailAndPassword(email, password);
    }

    @Override
    public UserBean checkByUsernameAndPassword(String username, String password) {
        return userMapper.checkByUsernameAndPassword(username, password);
    }

    @Override
    public int insertUserRole(int userID, int roleID) {
        return userMapper.insertUserRole(userID, roleID);
    }

    @Override
    public List<UserBean> getList() {
        return userMapper.selectAll();
    }

    @Override
    public UserBean findByUsername(String username) {
        return findByUsernameOrEmail(username);
    }

    @Override
    public UserBean findByEmail(String email) {
        return findByUsernameOrEmail(email);
    }

    @Override
    public UserBean findByID(int userID) {
        return userMapper.selectByID(userID);
    }

    @Override
    public void setPermission(List<PermissionBean> permissionBeanList) {
        for (PermissionBean permission : permissionBeanList) {
            permissionMapper.insert(permission);
        }
    }
}
