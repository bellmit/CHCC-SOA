package com.guihe.platform.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author CHCC
 * @FileName GetwayApplication
 * @Date 2020/12/27 8:10 上午
 * @Version 1.0
 * @Description TODO OAuth2授权网关
 */
@ComponentScan({"com.guihe.platform.**"})
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
