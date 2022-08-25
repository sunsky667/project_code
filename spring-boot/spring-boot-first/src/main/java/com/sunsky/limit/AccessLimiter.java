package com.sunsky.limit;

import java.util.concurrent.TimeUnit;

public interface AccessLimiter {

    /**
     * 检查指定的key是否收到访问限制
     * @param key   限制接口的标识
     * @param times 访问次数
     * @param per   一段时间
     * @param unit  时间单位
     * @return
     */
    public boolean isLimited(String key, long times, long per, TimeUnit unit);

    /**
     * 移除访问限制
     * @param key
     */
    public void refreshLimited(String key);

    /**
     * 接口是否打开
     * @return
     */
    public boolean isStatus(String redisKey);

    /**
     * 接口的限流大小
     * @param redisKeyTimes
     * @return
     */
    public long getTimes(String redisKeyTimes);

    /**
     * 接口限流时间段
     * @param redisKeyPer
     * @return
     */
    public long getPer(String redisKeyPer);

    /**
     * 接口的限流时间单位
     * @param redisKeyUnit
     * @return
     */
    public TimeUnit getUnit(String redisKeyUnit);

    /**
     * 是否删除接口限流
     * @param redisKeyIsRefresh
     * @return
     */
    public boolean getIsRefresh(String redisKeyIsRefresh);

}
