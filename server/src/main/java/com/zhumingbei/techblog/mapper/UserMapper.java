package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.UserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int insert(UserBean user);

    UserBean checkUser(String email, String password);

    void update(UserBean user);

    UserBean selectByToken(String token);

    UserBean selectByEmail(String email);

    int insertUserRole(int userID, int roleID);

    List<UserBean> selectAll();
}
