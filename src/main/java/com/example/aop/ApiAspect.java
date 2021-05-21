package com.example.aop;


import lombok.Data;
import lombok.Synchronized;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@Aspect
@Component
@ConfigurationProperties(prefix = "app")
@Data
public class ApiAspect {

    private static final Logger log = LoggerFactory.getLogger(ApiAspect.class);
    private Map<String, Integer> map;


    @Around(value = "@annotation(com.example.aop.ApiCall)")
    @Synchronized
    public Object maxApiCalls(ProceedingJoinPoint jp) throws Throwable {
        var methodName = jp.getSignature().getName();
        if (!map.containsKey(methodName)) {
            log.error("Для метода " + methodName + " не определено максимальное число вызовов");
            return null;
        }
        if (map.get(methodName) > 0) {
            map.put(methodName, map.get(methodName) - 1);
            return jp.proceed();
        } else {
            log.error(methodName + " больше вызывать нельзя");
        }
        return null;
    }
}
