package com.platform.util;

/**
 * 真假帮助类
 * 
 * @author Marker.King
 * 
 */
public final class TFHelper {

    /**
     * 判断字符串返回真假(T为真,其他为假)
     * 
     * @param tfString
     * @return
     */
    public static boolean trueOrFalse(String tfString) {
        if (!Validate.notNull(tfString))
            return false;
        tfString = tfString.toUpperCase();
        if (Validate.notNull(tfString) && "T".equals(tfString))
            return true;
        else
            return false;
    }

    /**
     * 判断数字返回真假(1为真,其他为假)
     * 
     * @param tfInteger
     * @return
     */
    public static boolean trueOrFalse(int tfInteger) {
        if (tfInteger == 1)
            return true;
        else
            return false;
    }
}
