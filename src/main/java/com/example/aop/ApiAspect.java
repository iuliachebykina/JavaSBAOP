package com.example.aop;


import lombok.Data;
import lombok.Synchronized;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Data
public class ApiAspect {

    private static final Logger log = LoggerFactory.getLogger(ApiAspect.class);
    private final Calls calls;

    public ApiAspect(Calls calls) {
        this.calls = calls;
    }

    @Around(value = "@annotation(com.example.aop.ApiCall)")
    @Synchronized
    public Object maxApiCalls(ProceedingJoinPoint jp) throws Throwable {
        var methodName = jp.getSignature().getName();
        var map = calls.getMap();
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
