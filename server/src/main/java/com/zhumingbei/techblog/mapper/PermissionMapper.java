package com.zhumingbei.techblog.mapper;

import com.zhumingbei.techblog.bean.PermissionBean;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PermissionMapper {
    void insert(PermissionBean permission);
}
