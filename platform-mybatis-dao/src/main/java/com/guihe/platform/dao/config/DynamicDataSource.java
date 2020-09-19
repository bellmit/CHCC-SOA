package com.guihe.platform.dao.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author CHCC
 * @FileName DynamicDataSource
 * @Date 2020/9/19 11:27 上午
 * @Version 1.0
 * @Description TODO
 */
public class DynamicDataSource  extends AbstractRoutingDataSource {

    //根据key获取数据源信息
    protected Object determineCurrentLookupKey() {
        return DynamicContextHolder.getDataSourceType();
    }
}
