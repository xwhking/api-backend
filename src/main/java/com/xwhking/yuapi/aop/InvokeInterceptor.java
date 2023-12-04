package com.xwhking.yuapi.aop;

import com.xwhking.yuapi.annotation.InvokeInterface;
import com.xwhking.yuapi.service.UserInterfaceInfoService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class InvokeInterceptor {
    @Resource
    UserInterfaceInfoService userInterfaceInfoService;
    @Around("@within(com.xwhking.yuapi.annotation.InvokeInterface)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint){
        System.out.println("Before");
        Object obj = new Object();
        try {
            obj = joinPoint.proceed();
            System.out.println("a little");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        System.out.println("After");
        return obj;
    }
}
