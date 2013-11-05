package com.platform.util;

/**
 * 分页帮助类
 * 
 * @author Marker.King
 */
public class PageHelper {

    /**
     * 获取第一条记录的位置 将传入的页码转换为所需要的实际第一条记录数，记录的索引为0开始
     * 
     * @param page
     * @param pageSize
     * @return
     */
    public static int getFirstRow(int pageNo, int pageSize) {
        if (pageNo <= 0)
            pageNo = 0;
        else
            pageNo--;
        return pageNo * pageSize;
    }

    /**
     * @param rowCount
     * @param pageSize
     * @return
     */
    public static int getMaxPage(int rowCount, int pageSize) {
        return rowCount % pageSize == 0 ? rowCount / pageSize : (rowCount / pageSize) + 1;
    }
}