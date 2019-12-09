package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.bean.RoleBean;
import com.zhumingbei.techblog.mapper.RoleMapper;
import com.zhumingbei.techblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleBean findByRoleName(String roleName) {
        return roleMapper.selectByRoleName(roleName);
    }
}
