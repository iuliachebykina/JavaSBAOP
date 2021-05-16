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

    @Around("@annotation(com.example.aop.FirstApiCall)")
    public Object FirstApiCall(ProceedingJoinPoint jp) throws Throwable {
        if (firstMaxCalls > 0) {
            var res = jp.proceed();
            firstMaxCalls--;
            return res;
        }
        log.warn("first api больше вызывать нельзя");
        return null;
    }

    @Around("@annotation(com.example.aop.SecondApiCall)")
    public Object SecondApiCall(ProceedingJoinPoint jp) throws Throwable {
        if (secondMaxCalls > 0) {
            var res = jp.proceed();
            secondMaxCalls--;
            return res;
        }
        log.warn("second api больше вызывать нельзя");
        return null;
    }
}
