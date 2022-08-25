package com.sunsky.limit;

import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

@Component
public class GenerateRedisKey {

    public String getMethodUrlConvertRedisKey(ProceedingJoinPoint joinPoint){
        StringBuilder redisKey =new StringBuilder("");
        Method m = ((MapperMethod.MethodSignature)joinPoint.getSignature()).getMethod();
        RequestMapping methodAnnotation = m.getAnnotation(RequestMapping.class);
        if (methodAnnotation != null) {
            String[] methodValue = methodAnnotation.value();
            String dscUrl = diagonalLineToCamel(methodValue[0]);
            return redisKey.append("RSK:").append("interfaceIsOpen:").append(dscUrl).toString();
        }
        return redisKey.toString();
    }
    private String diagonalLineToCamel(String param){
        char UNDERLINE='/';
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 1; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
