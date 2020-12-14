package com.guihe.platform.flowable.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author CHCC
 * @FileName ProcessEngineConfig
 * @Date 2020/11/25 9:33 上午
 * @Version 1.0
 * @Description TODO 流程引擎配置
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Data
public class ProcessEngineConfig {

    @Value("${spring.datasource.druid.url}")
    private String url;

    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.druid.username}")
    private String username;

    @Value("${spring.datasource.druid.password}")
    private String password;

    @Primary
    @Bean(name = "processEngine")
    public ProcessEngine initProcessEngine() {
        log.info("=============================ProcessEngineBegin=============================");

        // 流程引擎配置
        ProcessEngineConfiguration cfg = null;
        try {
            cfg = new StandaloneProcessEngineConfiguration()
                    .setJdbcDriver(driverClassName)
                    .setJdbcUrl(url)
                    .setJdbcUsername(username)
                    .setJdbcPassword(password)
                    // 初始化基础表，不需要的可以改为 DB_SCHEMA_UPDATE_FALSE
                    .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                    .setActivityFontName("宋体")
                    .setLabelFontName("宋体")
                    .setAnnotationFontName("宋体");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        ProcessEngine processEngine = cfg.buildProcessEngine();
        log.info("=============================ProcessEngineEnd=============================");
        return processEngine;
    }
}
