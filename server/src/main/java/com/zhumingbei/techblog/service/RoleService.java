package com.zhumingbei.techblog.service;

import com.zhumingbei.techblog.bean.RoleBean;

public interface RoleService {
    RoleBean findByRoleName(String roleName);

}
