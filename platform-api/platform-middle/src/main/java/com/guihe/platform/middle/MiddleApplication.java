package com.guihe.platform.middle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author CHCC
 * @FileName com.guihe.platform.middle.MiddleApplication
 * @Date 2020/9/15 9:11 上午
 * @Version 1.0
 * @Description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages ="com.guihe.platform.dao.mapper.**.**")
public class MiddleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleApplication.class, args);
    }

}
