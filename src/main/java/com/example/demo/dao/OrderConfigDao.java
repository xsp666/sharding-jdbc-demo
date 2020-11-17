package com.example.demo.dao;

import com.example.demo.Db1JdbcConfig;
import com.example.demo.OrderConfig;
import com.example.demo.mutil.DataSource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderConfigDao {

    @DataSource(value = Db1JdbcConfig.No_Sharding)
    @Insert("insert into order_config(pay_timeout) values(#{payTimeout})")
    int insertConfig1(Integer payTimeout);

    @DataSource(value = Db1JdbcConfig.No_Sharding)
    @Select("select id,pay_timeout from order_config")
    List<OrderConfig> selectConfigDB1();
}
