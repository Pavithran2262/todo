package com.todoapp.backend_application.Configuration;

import com.todoapp.backend_application.Entity.UserTable;
import com.todoapp.backend_application.ExceptionHandling.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoUserDetails implements UserDetails {
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    Logger log = LoggerFactory.getLogger(GlobalException.class);
    public UserInfoUserDetails(UserTable userTable) {
        try {
            log.debug("   ...............from  userinfouserdetails...usertable.................   ");
            name = userTable.getUserName();
            password = userTable.getPassword();
            authorities = Arrays.stream(userTable.getRole().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Name or Password cannot be null or empty");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

