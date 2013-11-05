package com.platform.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtil {

    /**
     * 添加缓存
     * 
     * @param cacheName 缓存策略名称
     * @param key 缓存键
     * @param value 缓存值
     */
    public static void put(String cacheName, String key, Object value) {
        Cache cache = CacheManager.getInstance().getCache(cacheName);
        if(value == null) {
        	cache.remove(key);
        } else {
        	cache.put(new Element(key, value));
        }
        cache.flush();
    }

    /**
     * 获得缓存
     * 
     * @param cacheName 缓存策略名称
     * @param key 缓存键
     * @return
     */
    public static Object get(String cacheName, String key) {
        Cache cache = CacheManager.getInstance().getCache(cacheName);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        } else {
            return element.getValue();
        }
    }

}