package com.zhumingbei.techblog.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String url;
    private Integer level;
    private Integer roleID;
}
