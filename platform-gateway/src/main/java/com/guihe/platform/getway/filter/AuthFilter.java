package com.guihe.platform.getway.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * @author CHCC
 * @FileName AuthFilter
 * @Date 2020/12/27 4:03 下午
 * @Version 1.0
 * @Description TODO 鉴权过滤器
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Resource
    private JwtTokenStore jwtTokenStore;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        //查看请求中有没有带jwt token
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isBlank(token)){
            return result(response);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> result(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String body="{\"code\":401,\"msg\":\"没有权限\"}";
        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        Mono<Void> voidMono = response.writeWith(Mono.just(buffer));
        return voidMono;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
