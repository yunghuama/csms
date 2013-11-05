package com.platform.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * 说明：验证类<br>
 * 作者：马金凯<br>
 * 更改记录： <br>
 * -------------------------------------------------------<br>
 * 改动人 时间 原因<br>
 * -------------------------------------------------------<br>
 * 马金凯 2007-12-1 创建文件<br>
 * -------------------------------------------------------<br>
 */
public final class Validate {

    /**
     * 判断传入的字符串是否为空(包括null和"")
     * 
     * @param String
     * @return boolean
     */
    public static boolean notNull(String arg) {
    	return !(arg == null || "".equals(arg));
    }

    /**
     * 反射判断对象的ID是否为空
     * 
     * @param Object
     *            对象实例
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public static boolean idNotNull(Object entity) {
        try {
            if (entity == null)
                return false;
            Class clazz = entity.getClass();
            Method method = clazz.getMethod("getId", new Class[] {});
            String id = (String) method.invoke(entity, new Object[] {});
            return !(id == null || "".equals(id));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    /**
     * 判断集合不为null,长度不为0
     * 
     * @param list
     * @return
     */
    public static boolean collectionNotNull(Collection collection) {
    	return collection != null && collection.size() > 0;
    }

    /**
     * 判断map不为null,长度不为0
     * 
     * @param map
     * @return
     */
    public static boolean mapNotNull(Map map) {
    	return map != null && map.size() > 0;
    }

    /**
     * 判断数组不为null,长度不为0
     * 
     * @param objs
     * @return
     */
    public static boolean arrayNotNull(Object[] objs) {
    	return objs != null && objs.length > 0;
    }
}