package com.guihe.platform.jiulong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CHCC
 * @FileName JiulongApplication
 * @Date 2020/12/26 4:10 下午
 * @Version 1.0
 * @Description TODO
 */
@RestController
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class JiulongApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiulongApplication.class, args);
    }

    @RequestMapping("/test")
    public String test(){
        return "hello";
    }


}
