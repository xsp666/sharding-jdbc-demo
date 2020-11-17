package com.example.demo.dao;

import com.example.demo.Db1JdbcConfig;
import com.example.demo.Orders;
import com.example.demo.mutil.DataSource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;


public interface OrdersDao {

    @DataSource(value = Db1JdbcConfig.DB_Sharding)
    List<Orders> select1(Long userId);

    @DataSource(value = Db1JdbcConfig.DB_Sharding)
    @Select(" select * from orders")
    List<Orders> select1All();

   @DataSource(value = Db1JdbcConfig.DB_Sharding)
    int insert1(Long userId);

    @DataSource(value = Db1JdbcConfig.DB_Sharding)
    List<Orders> select1In(@Param("userIds") List<Long> userIds);
}
