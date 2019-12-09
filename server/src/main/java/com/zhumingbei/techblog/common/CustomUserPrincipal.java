package com.zhumingbei.techblog.common;

import com.zhumingbei.techblog.bean.PermissionBean;
import com.zhumingbei.techblog.bean.RoleBean;
import com.zhumingbei.techblog.bean.UserBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserPrincipal implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String rawUsername;

    private String password;
    private String email;
    private Collection<? extends SimpleGrantedAuthority> authorities;
    private Set<String> urls;

    public static CustomUserPrincipal create(UserBean user, String username) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        Set<String> urls = new HashSet<>();
        for (RoleBean role : user.getRoleList()) {
            for (PermissionBean permission : role.getPermissionList()) {
                authorities.add(new SimpleGrantedAuthority(permission.getUrl()));
                urls.add(permission.getUrl());
            }
        }
        return new CustomUserPrincipal(username, user.getUsername(), user.getPassword(), user.getEmail(), authorities, urls);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
