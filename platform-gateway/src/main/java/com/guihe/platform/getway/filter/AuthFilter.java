package com.guihe.platform.getway.filter;

import com.alibaba.fastjson.JSONObject;
import com.guihe.platform.core.domain.Response;
import com.guihe.platform.getway.config.IgnoreUrlsConfig;
import com.guihe.platform.security.oauth.token.TokenRedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author CHCC
 * @FileName AuthFilter
 * @Date 2020/12/27 4:03 下午
 * @Version 1.0
 * @Description TODO OAuth2鉴权过滤器
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Resource
    private IgnoreUrlsConfig urlsConfig;

    @Resource
    private TokenRedisConfig tokenRedisConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        TokenStore tokenStore = tokenRedisConfig.tokenStore();
        List<String> urls = urlsConfig.getUrls();
        String jwtPrefix = urlsConfig.getJwtPrefix();
        RequestPath path = exchange.getRequest().getPath();
        if (urls.contains(path.value())) {
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        try {
            //查看请求中有没有带jwt token
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if(StringUtils.isBlank(token) && !token.startsWith(jwtPrefix)){
                return http401Result(response, Response.ResponseCode.UNAUTHORIZED);
            }
            //截取token
            token = StringUtils.substringAfter(token, jwtPrefix);

            //校验token
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
            int expires = oAuth2AccessToken.getExpiresIn();
            if(expires < 0) {
                return http401Result(response,Response.ResponseCode.TOKEN_INVALID);
            }
        }catch (Exception e){
            log.error("gateway-AuthFilter ERROR:",e);
            return http401Result(response,Response.ResponseCode.TOKEN_INVALID);
        }
        return chain.filter(exchange);
    }

    //返回响应
    private Mono<Void> http401Result(ServerHttpResponse response, Response.ResponseCode code){
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Response message = Response.builder(code).message(code.getMessage());
        String body = JSONObject.toJSONString(message);
        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        Mono<Void> voidMono = response.writeWith(Mono.just(buffer));
        return voidMono;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
