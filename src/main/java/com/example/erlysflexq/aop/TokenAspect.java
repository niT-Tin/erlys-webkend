package com.example.erlysflexq.aop;

import com.example.erlysflexq.config.shiro.ThreadLocalToken;
import com.example.erlysflexq.pojo.RqObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {

    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Pointcut("execution(public * com.example.erlysflexq.controller.*.*(..))")
    public void aspect(){
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        RqObject r = (RqObject)point.proceed();
        String token = threadLocalToken.getToken();
        if(token != null){
            r.setToken(token);
            threadLocalToken.clear();
        }
        return r;
    }

}
