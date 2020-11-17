package com.example.demo;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.sharding-jdbc1")
public class Db2JdbcConfig extends JdbcConfig{
    public static final String name = "sharding-jdbc1";
    public static final String No_Sharding = "no_sharding-jdbc1";
}
