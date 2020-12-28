package com.guihe.platform.dao.dynamic;

import com.guihe.platform.dao.config.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author CHCC
 * @FileName DynamicDataSourceProperties
 * @Date 2020/9/19 1:42 下午
 * @Version 1.0
 * @Description TODO
 */
@ConfigurationProperties(prefix = "dynamic")
public class DynamicDataSourceProperties {

    private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();

    public Map<String, DataSourceProperties> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DataSourceProperties> datasource) {
        this.datasource = datasource;
    }

}
