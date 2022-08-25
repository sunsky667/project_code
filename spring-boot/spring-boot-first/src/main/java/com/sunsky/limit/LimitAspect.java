package com.sunsky.limit;

import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LimitAspect {

    @Autowired
    private AccessLimiter limiter;

    @Autowired
    GenerateRedisKey generateRedisKey;

    @Pointcut("@annotation(com.mgtv.util.limit.Limit)")
    public void limitPointcut() {}

    @Around("limitPointcut()")
    public Object doArround(ProceedingJoinPoint joinPoint) throws Throwable {
        String redisKey = generateRedisKey.getMethodUrlConvertRedisKey(joinPoint);
        long per = limiter.getPer(redisKey);
        long times = limiter.getTimes(redisKey);
        TimeUnit unit = limiter.getUnit(redisKey);
        boolean isRefresh =limiter.getIsRefresh(redisKey);
        boolean methodLimitStatus = limiter.isStatus(redisKey);
        String bindingKey = genBindingKey(joinPoint);
        if (methodLimitStatus) {
            logger.info("method is closed, key: ", bindingKey);
            return ResponseDto.fail("40007", "method is closed, key:"+bindingKey);
            //throw new OverLimitException("method is closed, key: "+bindingKey);
        }
        if(bindingKey !=null){
            boolean isLimited = limiter.isLimited(bindingKey, times, per, unit);
            if(isLimited){
                logger.info("limit takes effect: {}", bindingKey);
                return ResponseDto.fail("40006", "access over limit, key: "+bindingKey);
                //throw new OverLimitException("access over limit, key: "+bindingKey);
            }
        }
        Object result = null;
        result = joinPoint.proceed();
        if(bindingKey!=null && isRefresh) {
            limiter.refreshLimited(bindingKey);
            logger.info("limit refreshed: {}", bindingKey);
        }
        return result;
    }

    private String genBindingKey(ProceedingJoinPoint joinPoint){
        try{
            Method m = ((MapperMethod.MethodSignature) joinPoint.getSignature()).getMethod();
            return joinPoint.getTarget().getClass().getName() + "." + m.getName();
        }catch (Throwable e){
            return null;
        }
    }

}
