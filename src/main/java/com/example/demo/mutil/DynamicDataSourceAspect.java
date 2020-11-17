package com.example.demo.mutil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    @Pointcut("@annotation(com.example.demo.mutil.DataSource)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {

        MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) joinPoint;

        Field proxy = methodPoint.getClass().getDeclaredField("methodInvocation");

        proxy.setAccessible(true);

        ReflectiveMethodInvocation j = (ReflectiveMethodInvocation) proxy.get(methodPoint);

        Method method = j.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);
        String value = dataSource.value();
        DynamicDataSourceContextHolder.setDataSourceName(dataSource.value());
    }

    @After("pointcut()")
    public void doAfter(JoinPoint point) {
        DynamicDataSourceContextHolder.clear();
    }
}

