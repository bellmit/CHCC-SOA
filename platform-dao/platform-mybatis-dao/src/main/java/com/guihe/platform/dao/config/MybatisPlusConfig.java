package com.guihe.platform.dao.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CHCC
 * @FileName MybatisConfig
 * @Date 2020/12/28 10:41 上午
 * @Version 1.0
 * @Description TODO mybatisPlus配置
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor interceptor = new PaginationInterceptor();
        return interceptor;
    }

}
