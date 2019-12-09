package com.zhumingbei.techblog.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer level;
    private List<PermissionBean> permissionList;
}
