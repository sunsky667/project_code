package com.sunsky.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisAccessLimiter implements AccessLimiter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisAccessLimiter.class);

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean isLimited(String key, long times, long per, TimeUnit unit) {
        Long curTimes = redisTemplate.boundValueOps(key).increment(1);
        LOGGER.info("curTimes {}",curTimes);
        if(curTimes > times) {
            LOGGER.debug("超频访问:[{}]",key);
            return true;
        } else {
            if(curTimes == 1) {
                LOGGER.info(" set expire ");
                redisTemplate.boundValueOps(key).expire(per, unit);
                return false;
            } else {
                return false;
            }
        }
    }


    public void refreshLimited(String key) {
        redisTemplate.delete(key);
    }

    public boolean isStatus(String redisKey) {
        try {
            return (boolean)redisTemplate.opsForValue().get(redisKey+"IsOn");
        }catch (Exception e){
            LOGGER.info("redisKey is not find or type mismatch, key: ", redisKey);
            return false;
        }
    }

    public long getTimes(String redisKeyTimes) {
        try {
            return Long.parseLong(redisTemplate.opsForValue().get(redisKeyTimes+"Times").toString());
        }catch (Exception e){
            LOGGER.info("redisKey is not find or type mismatch, key: ", redisKeyTimes);
            return 0;
        }
    }

    public long getPer(String redisKeyPer) {
        try {
            return (long)redisTemplate.opsForValue().get(redisKeyPer+"Per");
        }catch (Exception e){
            LOGGER.info("redisKey is not find or type mismatch, key: ", redisKeyPer);
            return 0;
        }
    }

    public TimeUnit getUnit(String redisKeyUnit) {
        try {
            return (TimeUnit) redisTemplate.opsForValue().get(redisKeyUnit+"Unit");
        }catch (Exception e){
            LOGGER.info("redisKey is not find or type mismatch, key: ", redisKeyUnit);
            return TimeUnit.SECONDS;
        }
    }

    public boolean getIsRefresh(String redisKeyIsRefresh) {
        try {
            return (boolean)redisTemplate.opsForValue().get(redisKeyIsRefresh+"IsRefresh");
        }catch (Exception e){
            LOGGER.info("redisKey is not find or type mismatch, key: ", redisKeyIsRefresh);
            return false;
        }
    }

}
