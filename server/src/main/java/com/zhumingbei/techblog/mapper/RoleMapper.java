package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.RoleBean;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {
    RoleBean selectByRoleNameAndUserID(String roleName, int userID);
    RoleBean findByRoleName(String roleName);
    int insert(RoleBean role);
}
