package com.lin.security.utils;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {

    private final RedisTemplate redisTemplate;

    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存基本对象，Integer、String、实体类
     * @param key：缓存的key
     * @param value：缓存的value
     * @param <T>：声明一个泛型方法
     */
    public <T> void setCacheObject(final String key,final T value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 缓存基本对象，Integer、String、实体类
     * @param key
     * @param value
     * @param timeOut：缓存过期时间
     * @param timeUnit：时间单位
     * @param <T>
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeOut, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,timeOut,timeUnit);
    }

    /**
     * 设置缓存有效时间
     * @param key
     * @param timeOut
     * @param timeUnit
     * @return boolean：ture-设置成功
     */
    public boolean expire(final String key,final long timeOut,final TimeUnit timeUnit){
        return redisTemplate.expire(key,timeOut,timeUnit);
    }

    /**
     * 获取缓存
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getCacheObject(final String key){
        ValueOperations<String,T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 删除单个缓存对象
     * @param key
     * @return
     */
    public boolean deleteCacheObject(final String key){
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合缓存对象
     * @param collection：多个缓存对象
     * @return
     */
    public boolean deleteCacheObject(final Collection collection){
        Long delete = redisTemplate.delete(collection);
        return delete == null ? false : delete > 0 ? true : false;
    }

    /**
     * 缓存list数据
     * @param key
     * @param list：list集合
     * @param <T>
     * @return
     */
    public <T> boolean setCacheList(final String key, final List<T> list){
        Long pushAll = redisTemplate.opsForList().rightPushAll(key, list);
        return pushAll == null ? false : pushAll > 0 ? true : false;
    }

    /**
     * 获取缓存的list对象
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getCacheList(final String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }

    /**
     * 缓存set集合
     * @param key
     * @param set
     * @param <T>
     * @return
     */
    public <T> BoundSetOperations<String,T> setCacheSet(final String key,final Set<T> set){
        BoundSetOperations<String,T> setOperations = redisTemplate.boundSetOps(key);
        Iterator<T> iterator = set.iterator();
        while (iterator.hasNext()){
            setOperations.add(iterator.next());
        }
        return setOperations;
    }

    /**
     * 获取缓存的set对象
     * @param key
     * @param <T>
     * @return
     */
    public <T> Set<T> getCacheSet(final String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     * @param key
     * @param map
     * @param <T>
     */
    public <T> void setCacheMap(final String key, final Map<String,T> map) {
        if(map != null){
            redisTemplate.opsForHash().putAll(key,map);
        }
    }

    /**
     * 获取缓存的Map对象
     * @param key
     * @param <T>
     * @return
     */
    public <T> Map<String,T> getCacheMap(final String key){
       return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中缓存数据
     * @param key
     * @param hashKey
     * @param value
     * @param <T>
     */
    public <T> void setCacheHash(final String key,final String hashKey,final T value){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    /**
     * 获取Hash中缓存的单个数据
     * @param key
     * @param hashKey
     * @param <T>
     * @return
     */
    public <T> T getCacheHash(final String key,final  String hashKey){
        HashOperations<String,String,T> operations = redisTemplate.opsForHash();
        return operations.get(key,hashKey);
    }

    /**
     * 删除缓存Hash的单个key对象
     * @param key
     * @param hashKey
     * @return
     */
    public boolean delCacheMapValue(final String key, final String hashKey){
        HashOperations operations = redisTemplate.opsForHash();
        Long delete = operations.delete(key, hashKey);
        return delete == null ? false : delete > 0 ? true : false;
    }

    /**
     * 获取缓存Hash中多个value
     * @param key
     * @param hashKeys：collection集合
     * @param <T>
     * @return
     */
    public <T> List<T> getMultiCacheHashValue(final String key,final Collection<Object> hashKeys){
        return redisTemplate.opsForHash().multiGet(key,hashKeys);
    }

    /**
     * 获取缓存的基本对象列表
     * @param pattern：字符串前缀
     * @return
     */
    public Collection<String> keys(final String pattern){
        return redisTemplate.keys(pattern);
    }
}
