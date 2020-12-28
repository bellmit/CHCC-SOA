package com.guihe.platform.getway.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author CHCC
 * @FileName IgnoreUrlsConfig
 * @Date 2020/12/28 10:22 上午
 * @Version 1.0
 * @Description TODO 网关白名单配置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {

    private String jwtPrefix;

    private List<String> urls;
}
