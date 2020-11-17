package com.example.demo;

public class OrderConfig {
    private Long id;
    private Integer payTimeout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPayTimeout() {
        return payTimeout;
    }

    public void setPayTimeout(Integer payTimeout) {
        this.payTimeout = payTimeout;
    }
}
