package com.sunsky.interceptor;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@EnableAspectJAutoProxy
@Aspect
public class ServiceInterceptor {

    @Around("execution(* com.sunsky.service.*.*.*(..)))")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        System.err.println("==================>" + JSON.toJSONString(result));
        return result;
    }

}
