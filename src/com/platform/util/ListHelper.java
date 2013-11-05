package com.platform.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListHelper {

    /**
     * 将idList中不能删除的ID移除,返回List集合(下标0为可以删除的集合,下标1为不可以删除的集合)
     * 
     * @param idList
     * @param cannotDeleteData
     * @return
     */
    public static List<List<String>> getDifferent(List<String> idList, List<String> cannotDeleteData) {
        for (Serializable id : cannotDeleteData) {
            idList.remove(id);
        }
        List<List<String>> differentList = new ArrayList<List<String>>();
        differentList.add(idList);
        differentList.add(cannotDeleteData);
        return differentList;
    }
    
    
    /**
     * 将List中的各String拼接成一个字符串
     * @param list 待处理的List
     * @param separator 分隔符
     * @return
     */
    public static String joint2String(List<String> list, String separator) {
    	if(!Validate.collectionNotNull(list)){
    		return null;
    	}
    	StringBuffer sb = new StringBuffer(1024);
	    for(String s : list){
	    	sb.append(s+separator);
	    }
    	return sb.toString();
    }
    
    /**
     * 将List中的各String拼接成一个查询字符串,in 或 not in使用
     * @param list 待处理的List
     * @return
     */
    public static String convertListToString(List<String> list) {
        StringBuffer sb = new StringBuffer("(");
        if(!Validate.collectionNotNull(list)){
            return sb.append("'')").toString();
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                sb.append("'").append(list.get(i)).append("'");
            }else {
                sb.append(",'").append(list.get(i)).append("'");
            }
        }
        sb.append(")");
        
        return sb.toString();
    }
    
    
}