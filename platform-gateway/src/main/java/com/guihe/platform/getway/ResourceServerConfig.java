package com.guihe.platform.getway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author CHCC
 * @FileName ResourceServerConfig
 * @Date 2020/12/27 2:02 下午
 * @Version 1.0
 * @Description TODO Gateway授权服务器配置
 */
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers("/auth/**").permitAll()
                .and()
                .csrf().disable();
        return http.build();
    }

}
