package com.guihe.platform.sso.service.impl;

import com.guihe.platform.sso.service.MyUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
