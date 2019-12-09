package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.UserBean;

import java.util.List;

public interface UserService {

    int insert(UserBean user);

    UserBean findByEmail(String email);

    void update(UserBean user);

    UserBean findByToken(String token);

    UserBean checkByEmailAndPassword(String email, String password);

    int insertUserRole(int userID, int roleID);

    List<UserBean> getList();
}
