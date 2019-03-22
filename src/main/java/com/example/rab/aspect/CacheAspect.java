package com.example.rab.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CacheAspect {

    public Map<String, Object> cacheMap = new HashMap<>();

    @Around("@annotation(com.example.rab.annotation.Cache)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String key = "name = " + Arrays.toString(pjp.getArgs());
        String value = Arrays.toString(pjp.getArgs());
        if (cacheMap.containsKey(key)) {
            return cacheMap.get(key);
        } else {
            pjp.proceed();
            cacheMap.putIfAbsent(key, value);
            return cacheMap.get(key);
        }
    }
}
