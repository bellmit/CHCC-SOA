package com.guihe.platform.dao.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author CHCC
 * @FileName DynamicContextHolder
 * @Date 2020/9/19 11:22 上午
 * @Version 1.0
 * @Description TODO 多数据源上下文
 */
@Slf4j
public class DynamicContextHolder {

    private  static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    /**
     * 获得当前线程数据源
     *
     * @return 数据源名称
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * @Description TODO 设置数据源变量
     * @Author CHCC
     * @Date 2020/9/19 11:25 上午
     * @params  * @param dataSourceType 数据源枚举
     * @returen void
     **/
    public static void setDataSourceType(String dataSourceType){
        log.info("切换到{}数据源",dataSourceType);
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * @Description TODO 清空数据源
     * @Author CHCC
     * @Date 2020/9/19 11:26 上午
     * @params  * @param
     * @returen void
     **/
    public static void clearDataSourceType(){
        CONTEXT_HOLDER.remove();
    }

}
