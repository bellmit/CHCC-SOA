package com.guihe.platform.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author CHCC
 * @FileName SSOServer
 * @Date 2020/8/4 9:09 上午
 * @Version 1.0
 * @Description TODO SSO启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SSOServer {

    public static void main(String[] args) {
        SpringApplication.run(SSOServer.class, args);
    }

}
