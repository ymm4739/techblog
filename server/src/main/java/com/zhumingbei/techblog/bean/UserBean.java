package com.zhumingbei.techblog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Integer id;
    private String username;
    private String password;
    private String email;

    public Users(String username, String email, String password){
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
