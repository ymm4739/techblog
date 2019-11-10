package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.Users;
import com.zhumingbei.techblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

    int insert(Users user);
}
