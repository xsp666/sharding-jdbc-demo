package com.example.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.mutil.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



@Configuration
@EnableTransactionManagement
@MapperScan("com.example.demo.dao")
public class MybatisConfig {

    @Bean("dataSource")
    public DataSource basicDataSource(Db1JdbcConfig db1JdbcConfig,Db2JdbcConfig db2JdbcConfig) throws SQLException {
        DataSource db1Source = createDataSource(db1JdbcConfig);
        DataSource db2Source = createDataSource(db2JdbcConfig);

        Map<String, DataSource> db1DataSourceMap = new HashMap<>(2);
        db1DataSourceMap.put(Db1JdbcConfig.name, db1Source);
        db1DataSourceMap.put(Db2JdbcConfig.name, db2Source);


        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(db1DataSourceMap, createDB1shardingRuleConfig(), properties());
        DynamicDataSource dynamicDataSource=new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(db1Source);


        targetDataSources.put(Db1JdbcConfig.DB_Sharding,dataSource);

        targetDataSources.put(Db1JdbcConfig.No_Sharding,db1Source);
        targetDataSources.put(Db2JdbcConfig.No_Sharding,db2Source);

        Map<String, DataSource> accountSharding = new HashMap<>(2);
        accountSharding.put(Db1JdbcConfig.name, db1Source);

        DataSource account_DB_Sharding = ShardingDataSourceFactory.createDataSource(accountSharding, createDB1AccountShardingRuleConfig(), properties());
        targetDataSources.put(Db1JdbcConfig.Account_DB_Sharding,account_DB_Sharding);
        dynamicDataSource.setTargetDataSources(targetDataSources);

        return dynamicDataSource;
    }

    private Properties properties() {
        Properties p = new Properties();
        p.setProperty("sql.show", Boolean.TRUE.toString());
        p.setProperty("check.table.metadata.enabled",Boolean.TRUE.toString());
        return p;
    }


    private ShardingRuleConfiguration createDB1AccountShardingRuleConfig(){
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
       // shardingRuleConfig.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("account_id","sharding-jdbc0.account_${0..4}"));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_id",new AccountShardingStrategy()));
        //如果有多个数据表需要分表，依次添加到这里
        shardingRuleConfig.setBindingTableGroups(Arrays.asList("account"));
        shardingRuleConfig.getTableRuleConfigs().addAll(Arrays.asList(getDB1AccountTableRuleConfiguration()));
        return shardingRuleConfig;
    }

    //分库分表
    private ShardingRuleConfiguration createDB1shardingRuleConfig(){
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",new CustomShardingTableAlgorithm()));
        //第一种
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", " sharding-jdbc${user_id % 2}"));
        //第二种
        //shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",new DataBaseShardingStrategy()));
        //如果有多个数据表需要分表，依次添加到这里
        shardingRuleConfig.setBindingTableGroups(Arrays.asList("orders"));
        shardingRuleConfig.getTableRuleConfigs().addAll(Arrays.asList(getDB1OrderTableRuleConfiguration()));
        return shardingRuleConfig;
    }

    /**
     * KeyGeneratorConfiguration的构造参数type 自增列值生成器类型，可自定义或选择内置类型：SNOWFLAKE/UUID
     *
     *
     * @return
     */
    @Bean
    public TableRuleConfiguration getDB1OrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("orders","sharding-jdbc${0..1}.orders_${0..3}");
        result.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE","id"));
        return result;
    }

    @Bean
    public TableRuleConfiguration getDB1AccountTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("account","sharding-jdbc0.account_${0..4}");
        result.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE","id"));
        return result;
    }

    @Bean
    @Qualifier("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(@Autowired DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
       // configuration.addInterceptor(new PageInterceptor());
        sqlSessionFactoryBean.setConfiguration(configuration);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Autowired SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    public DataSource createDataSource(JdbcConfig jdbcConfig) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcConfig.getDriverClassName());
        dataSource.setUrl(jdbcConfig.getUrl());
        dataSource.setUsername(jdbcConfig.getUsername());
        dataSource.setPassword(jdbcConfig.getPassword());
        dataSource.setMaxActive(64);
        dataSource.setMinIdle(16);
        dataSource.setMaxWait(60000);
        return dataSource;
    }

}
