package com.guihe.platform.jiulong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author CHCC
 * @FileName JiulongApplication
 * @Date 2020/12/26 4:10 下午
 * @Version 1.0
 * @Description TODO
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.guihe.platform.**"})
@MapperScan(basePackages ="com.guihe.platform.dao.mapper.jiulong.**")
public class JiulongApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiulongApplication.class, args);
    }

}
