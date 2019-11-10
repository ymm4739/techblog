package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int insert(Users user);
}
