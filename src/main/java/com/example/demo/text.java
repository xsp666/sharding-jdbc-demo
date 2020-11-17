package com.example.demo;

import groovy.util.logging.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class text {
    private static final Object KEY = new Object();

    private static boolean taskFlag = false;

    @Scheduled(cron = "*/5 * * * * ?")
    public void pushCancel() throws InterruptedException {
        System.out.println("进来了");
//        synchronized (KEY) {
//            if (TestCrontab.taskFlag) {
//                System.out.println("测试调度已经启动");
//                log.warn("测试调度已经启动");
//                return;
//            }
//            TestCrontab.taskFlag = true;
//        }
//
//        try {
        for (int i =0;i<=10;i++){
            System.out.println("执行："+i);
            Thread.sleep(2000);
        }
//        } catch (Exception e) {
//            log.error("测试调度执行出错", e);
//        }
//
//        TestCrontab.taskFlag = false;
        System.out.println("测试调度执行完成");
    }


//    public static void main(String[] args) {
//        List<A> list=new ArrayList<>();
//        List<A> listB = Collections.synchronizedList(new ArrayList<A>());
//        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
//        CompletableFuture<Void> a = CompletableFuture.runAsync(() -> {
//            for (int i = 0; i <1000 ; i++) {
//                list.add(new A(1,"熊"));
//                listB.add(new A(1,"熊"));
//            }
//        });
//
//        CompletableFuture<Void> b = CompletableFuture.runAsync(() -> {
//            for (int i = 0; i <1000 ; i++) {
//                list.add(new A(1,"熊"));
//                listB.add(new A(1,"熊"));
//            }
//        });
//        CompletableFuture<Void> future = CompletableFuture.allOf(a, b);
//        future.join();
//        int size = list.size();
//        System.out.println(size);
//        int sizeB = listB.size();
//        System.out.println(sizeB);
//    }
    public static class A{
        private Integer age;
        private String name;

        public A(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



}
