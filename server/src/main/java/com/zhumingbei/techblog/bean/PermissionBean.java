package com.zhumingbei.techblog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String url;
    private Integer level;
    private Integer roleID;
    private Integer userID;

    public PermissionBean(int userID, int roleID, String url) {
        this.userID = userID;
        this.roleID = roleID;
        this.url = url;
        this.level = 0;
    }
}
