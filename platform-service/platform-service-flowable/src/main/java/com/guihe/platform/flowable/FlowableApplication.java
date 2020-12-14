package com.guihe.platform.flowable;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author CHCC
 * @FileName FlowableApplication
 * @Date 2020/11/25 9:30 上午
 * @Version 1.0
 * @Description TODO
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages ="com.guihe.platform.dao.mapper.flowable.**")
public class FlowableApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
    }

    //分页
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor interceptor = new PaginationInterceptor();
        return interceptor;
    }

}
