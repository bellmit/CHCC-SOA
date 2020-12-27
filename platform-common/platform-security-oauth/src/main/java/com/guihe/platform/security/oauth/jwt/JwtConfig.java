package com.guihe.platform.security.oauth.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author CHCC
 * @FileName JwtConfig
 * @Date 2020/12/27 4:30 下午
 * @Version 1.0
 * @Description TODO 初始化JWT配置
 */
@Configuration
public class JwtConfig {

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("Gui-he-platform");
        return jwtAccessTokenConverter;
    }

}
