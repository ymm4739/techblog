package com.zhumingbei.techblog.common;

import com.zhumingbei.techblog.bean.PermissionBean;
import com.zhumingbei.techblog.bean.RoleBean;
import com.zhumingbei.techblog.bean.UserBean;
import com.zhumingbei.techblog.mapper.RoleMapper;
import com.zhumingbei.techblog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Slf4j
public class CustomUserPrincipal implements UserDetails, Serializable {
    @Autowired
    private RoleMapper roleMapper;
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private Collection<? extends SimpleGrantedAuthority> authorities;

    public CustomUserPrincipal(int id, String username, String password,Collection<? extends SimpleGrantedAuthority> authorities){
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.id = id;
    }

    public  CustomUserPrincipal create(UserBean user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        Set<String> roleSet = new HashSet<>();
        List<RoleBean> roles = new ArrayList<>();
        for (RoleBean role : user.getRoleList()) {
            RoleBean r = roleMapper.selectByRoleNameAndUserID(role.getName(), user.getId());
            roles.add(r);
        }

        for (RoleBean role : roles) {
            for (PermissionBean permission : role.getPermissionList()) {
                authorities.add(new SimpleGrantedAuthority(permission.getUrl()));
                roleSet.add(role.getName());
            }
        }
        return new CustomUserPrincipal(user.getId(), user.getUsername(), user.getPassword(), authorities);
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

    public static Integer  getUserID() {
        CustomUserPrincipal principal =(CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }

    public static CustomUserPrincipal createGuest() {

        return new CustomUserPrincipal(0, "guest", null, null);
    }
}
