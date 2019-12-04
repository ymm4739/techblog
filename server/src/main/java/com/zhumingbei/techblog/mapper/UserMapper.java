package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.UserBean;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int insert(UserBean user);
    UserBean checkUser(String email, String password);
    void update(UserBean user);
    UserBean selectByToken(String token);
}
