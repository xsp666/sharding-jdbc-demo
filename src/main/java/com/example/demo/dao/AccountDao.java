package com.example.demo.dao;

import com.example.demo.Account;
import com.example.demo.Db1JdbcConfig;
import com.example.demo.mutil.DataSource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountDao {

    @DataSource(value =Db1JdbcConfig.Account_DB_Sharding)
    @Insert("insert into account(account_id) values(#{accountId})")
    int insertAccount(Long accountId);


    @DataSource(value =Db1JdbcConfig.Account_DB_Sharding)
    @Select("select * from account")
    List<Account> getAccount();
}
