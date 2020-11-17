package com.example.demo;


import com.example.demo.dao.AccountDao;
import com.example.demo.dao.OrderConfigDao;
import com.example.demo.dao.OrdersDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@SpringBootApplication
@RestController
//@MapperScan("com.example.demo.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Resource
    private OrdersDao ordersDao;
    @Resource
    private OrderConfigDao orderConfigDao;
    @Resource
    private AccountDao accountDao;


    @GetMapping("/insert1")
    public boolean insert1(){
        Long i= RandomUtils.nextLong(0,100);
        System.out.println(i);

        int insert = ordersDao.insert1(i);
        return insert>0;
    }

    @GetMapping("/select1/{id}")
    public  List<Orders> select1(@PathVariable("id") Long id){
        List<Orders> select = ordersDao.select1(id);
        return select;
    }

    @GetMapping("/select1All")
    public  List<Orders> select1All(){
        List<Orders> select = ordersDao.select1All();
        return select;
    }

    @PostMapping("/select1In")
    public  List<Orders> select1All(@RequestBody List<Long> userIds){
        List<Orders> select = ordersDao.select1In(userIds);
        return select;
    }

    @GetMapping("/insertConfig1")
    public boolean insertConfig(){
        int i= RandomUtils.nextInt(10,100);
        System.out.println(i);

        int insert = orderConfigDao.insertConfig1(i);
        return insert>0;
    }

    @GetMapping("/selectConfigDB1")
    public List<OrderConfig> select(){
        return orderConfigDao.selectConfigDB1();
    }

    @GetMapping("/insertAccount")
    public boolean insert2(){
        Long i= RandomUtils.nextLong(5,1000);
        System.out.println(i);

        int insert = accountDao.insertAccount(i);
        return insert>0;
    }


    @GetMapping("/getAccount")
    public  List<Account> getAccount(){
        List<Account> select = accountDao.getAccount();
        return select;
    }
}
