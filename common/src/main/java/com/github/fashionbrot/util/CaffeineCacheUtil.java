package com.github.fashionbrot.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CaffeineCacheUtil {

    private static Map<Long,Object> cache =new ConcurrentHashMap<>();

    public static void setCache(Long key ,Object value){
        cache.put(key,value);
    }

    public static Object getCache(Long key){
        return cache.get(key);
    }

    public static void clear(Long key){
        cache.remove(key);
    }


    public static void main(String[] args) {
        setCache(1L,"abc");
        clear(1L);
        System.out.println(cache);
    }

}
