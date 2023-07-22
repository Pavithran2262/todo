package com.todoapp.backend_application.Configuration;

import com.todoapp.backend_application.Entity.UserTable;
import com.todoapp.backend_application.ExceptionHandling.GlobalException;
import com.todoapp.backend_application.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    Logger log = LoggerFactory.getLogger(GlobalException.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserTable> userInfo = repository.findByUsername(username);
        log.debug("   ...............from  userinfouserdetails service...usertable...||.username .."+username+"..||.optional(userinfo).."+userInfo+"........   ");
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("....User not found : " + username));
    }
}






















// @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<UserTable> userInfo = Optional.ofNullable(repository.findByuserName(username));
//
//        return userInfo.map(UserInfoUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
//    }
