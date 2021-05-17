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

    @Value("${app.first_api_max_calls}")
    private int firstMaxCalls;
    @Value("${app.second_api_max_calls}")
    private int secondMaxCalls;
    private static final Logger log = LoggerFactory.getLogger(ApiAspect.class);

    @Around(value = "@annotation(com.example.aop.ApiCall)")
    public Object apiCall(ProceedingJoinPoint jp) throws Throwable {
        Object result = null;
        var methodName = jp.getSignature().getName();
        if (methodName.equals("firstApi")) {
            if (firstMaxCalls > 0) {
                firstMaxCalls--;
                result = jp.proceed();
            } else {
                log.warn(methodName + " больше вызывать нельзя");
            }
        } else if (secondMaxCalls > 0) {
            secondMaxCalls--;
            result = jp.proceed();
        } else {
            log.warn(methodName + " больше вызывать нельзя");
        }
        return result;
    }
}
