package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.UserBean;

import java.util.List;

public interface UserService {

    int insert(UserBean user);

    UserBean findByUsernameOrEmail(String s);

    void update(UserBean user);

    UserBean findByToken(String token);

    UserBean checkByEmailAndPassword(String email, String password);

    UserBean checkByUsernameAndPassword(String username, String password);

    int insertUserRole(int userID, int roleID);

    List<UserBean> getList();

    UserBean findByUsername(String username);

    UserBean findByEmail(String email);

    UserBean findByID(int userID);
}
