package com.guihe.platform.sso.config;

import com.guihe.platform.security.oauth.jwt.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import javax.sql.DataSource;

/**
 * @author chengcheng
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 配置token存储，这个配置token存到redis中
     * @return
     */
//    @Bean
//    public TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }

    /**
     * 配置授权码模式授权码服务,不配置默认为内存模式
     * @return
     */
//    @Primary
//    @Bean
//    public AuthorizationCodeServices authorizationCodeServices() {
//        return new RedisAuthorizationCodeServices(redisConnectionFactory);
//    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.allowFormAuthenticationForClients();
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("isAuthenticated()");
        //资源服务器验证token不进行拦截
        security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtConfig.jwtAccessTokenConverter());
        endpoints.tokenStore(jwtConfig.jwtTokenStore());
    }

}
