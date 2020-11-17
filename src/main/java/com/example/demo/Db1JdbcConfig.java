package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.sharding-jdbc0")
public class Db1JdbcConfig extends JdbcConfig{

    public static final String name = "sharding-jdbc0";

    public static final String No_Sharding = "No_Sharding_jdbc0";

    public static final String DB_Sharding = "DB_Sharding";

    public static final String Account_DB_Sharding = " Account_DB_Sharding";
}
