package com.example.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ApiAspect {


    @Value("${app.max_calls}")
    private int maxCalls;
    private static final Logger log = LoggerFactory.getLogger(ApiAspect.class);

    @Around("@annotation(com.example.aop.ApiCall)")
    public Object logMethodCalls(ProceedingJoinPoint jp) throws Throwable {
        if (maxCalls > 0) {
            var res = jp.proceed();
            maxCalls--;
            return res;
        }
        log.error("api больше вызывать нельзя");
        return null;
    }
}
