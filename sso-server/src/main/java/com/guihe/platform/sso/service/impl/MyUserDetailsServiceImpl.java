package com.guihe.platform.sso.service.impl;

import com.guihe.platform.sso.service.MyUserDetailsService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHCC
 * @FileName MyUserDetailsServiceImpl
 * @Date 2020/8/4 10:07 上午
 * @Version 1.0
 * @Description TODO
 */
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("admin");

        String role = "ADMIN_ROLE";
        List<SimpleGrantedAuthority> authorities = new ArrayList();
        authorities.add(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(
                "admin",
                password ,
                authorities);
    }
}
