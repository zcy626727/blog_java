package com.zcy.blog.config.redis;


import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCache implements Cache {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id;
    private RedisTemplate redisTemplate;
    //redis过期时间
    private static final long EXPIRE_TIME_IN_MINUTES = 30;

    public RedisCache(String id){
        if (id == null){
            throw  new IllegalArgumentException("Cache instance required an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
    /**
     * Put query result to redis
     * @Param key
     * @Param value
     * */
    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = getRedisTemplate();
        ValueOperations opsForValue = redisTemplate.opsForValue();

        if(value!=null){
            opsForValue.set(key.toString(),value,EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
            System.out.println("结果成功放入缓存 and "+"key = " +"\n"+ key + "value = " + value);
        }
    }
    /**
     * Get cached query result to redis
     * @Param key
     * @Return
     * */
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        ValueOperations opsForValue = redisTemplate.opsForValue();
        System.out.println("结果从缓存中获取");
        return opsForValue.get(key.toString());
    }
    /**
     * Remove cached query result to redis
     * @Param key
     * @Return
     * */
    @Override
    public Object removeObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.delete(key);
        System.out.println("从缓存中删除");
        return null;
    }
    /**
     * Clear this cache instance
     * */
    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
        System.out.println("清空缓存");
    }

    @Override
    public int getSize() {
        Long size = (Long) redisTemplate.execute((RedisCallback) connection -> connection.dbSize());
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate() {
        if(redisTemplate == null){
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
