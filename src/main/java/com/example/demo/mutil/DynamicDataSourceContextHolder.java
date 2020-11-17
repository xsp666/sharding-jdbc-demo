package com.example.demo.mutil;


public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    // 设置数据源
    public static void setDataSourceName(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    // 获取数据源
    public static String getDataSourceName() {

        return contextHolder.get();

    }

    // 获取数据源
    public static void clear() {
        contextHolder.remove();
    }
}

