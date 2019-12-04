package com.zhumingbei.techblog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBean implements Serializable {
    private static final Long serialVersionID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String token;
    private Integer isRememberMe;
    public UserBean(String username, String email, String password){
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
