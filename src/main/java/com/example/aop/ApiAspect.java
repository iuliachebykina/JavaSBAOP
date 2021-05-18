package com.example.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;


@Aspect
@Component
@Scope("prototype")
public class ApiAspect {

    @Value("${app.first_api_max_calls}")
    private int firstMaxCalls;
    @Value("${app.second_api_max_calls}")
    private int secondMaxCalls;
    private static final Logger log = LoggerFactory.getLogger(ApiAspect.class);
    private static final Map<String, AtomicInteger> map = new TreeMap<>();


    @Around(value = "@annotation(com.example.aop.ApiCall)")
    public Object apiCall(ProceedingJoinPoint jp) throws Throwable {
        var methodName = jp.getSignature().getName();
        if (!map.containsKey(methodName)) {
            if (methodName.equals("firstApi")) {
                map.put(methodName, new AtomicInteger(firstMaxCalls));
            } else if (methodName.equals("secondApi")) {
                map.put(methodName, new AtomicInteger(secondMaxCalls));
            }
        }
        if (map.get(methodName).get() > 0) {
            map.get(methodName).decrementAndGet();
            return jp.proceed();
        } else {
            log.warn(methodName + " больше вызывать нельзя");
        }
        return null;
    }


}
