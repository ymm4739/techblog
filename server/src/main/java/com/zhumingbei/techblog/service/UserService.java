package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.UserBean;

public interface UserService {

    int insert(UserBean user);
    UserBean findByEmail(String email, String password);
    void update(UserBean user);
    UserBean findByToken(String token);
}
