package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.UserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int insert(UserBean user);

    UserBean checkByEmailAndPassword(String email, String password);

    UserBean checkByUsernameAndPassword(String username, String password);

    void update(UserBean user);

    UserBean selectByToken(String token);

    UserBean selectByUsernameOrEmail(String s);

    int insertUserRole(int userID, int roleID);

    List<UserBean> selectAll();


}
