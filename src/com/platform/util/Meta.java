package com.platform.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.domain.RoleUsers;
import com.platform.domain.Users;
import com.platform.vo.Page;
import com.platform.vo.RoleCache;

/**
 * 权限工具类,用以获得各种权限信息
 * 
 * @author MarkerKing
 *
 */
public class Meta {

    /**
     * 获得操作权限
     * 
     * @param webId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getOperate(String webId) {
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, LoginBean.getLoginBean().getUser().getId());
        HashMap<String, String> operate = roleCache.getOperate();
        if (operate != null) {
            String useable = operate.get(webId);
            if (Validate.notNull(useable))
                return useable;
            else
                return StringConstant.FALSE;
        } else
            return StringConstant.FALSE;
    }

    /**
     * 获得数据范围内记录的操作权限
     * 
     * @param usersId
     * @param webId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getOtherOperate(String usersId, String webId) {
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, LoginBean.getLoginBean().getUser().getId());
        HashMap<String, HashMap<String, String>> operate = roleCache.getOtherOperates();
        if (operate != null) {
            HashMap<String, String> usersOperates = operate.get(usersId);
            if (usersOperates != null) {
                String useable = usersOperates.get(webId);
                if (Validate.notNull(useable))
                    return useable;
                else
                    return StringConstant.FALSE;
            } else
                return StringConstant.FALSE;
        } else
            return StringConstant.FALSE;
    }
    
    
    /**
     * 获得部门权限操作范围
     * @param departmentId
     * @param webId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getDepartmentOperate(String departmentId, String webId) {
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, LoginBean.getLoginBean().getUser().getId());
        HashMap<String, HashMap<String, String>> operate = roleCache.getDepartmentOperates();
        if (operate != null) {
            HashMap<String, String> departmentOperates = operate.get(departmentId);
            
            if (departmentOperates != null) {
                String useable = departmentOperates.get(webId);
                if (Validate.notNull(useable))
                    return useable;
                else
                    return StringConstant.FALSE;
            } else
                return StringConstant.FALSE;
        } else
            return StringConstant.FALSE;
    }

    
    @SuppressWarnings("unchecked")
    public static String getUsers(String moduleId) {
        return getUsers(LoginBean.getLoginBean().getUser(),moduleId );
    }
    
    /**
     * 获得当前用户的角色ID
     */
    public static String getRoleIds(List<RoleUsers> ruList){
    	StringBuilder sb = new StringBuilder();
    	for(RoleUsers ru : ruList){
    		String id = ru.getRole().getId();
    		sb.append("'");
    		sb.append(id);
    		sb.append("',");
    	}
    	System.out.println(sb.toString());
    	return sb.substring(0,sb.lastIndexOf(",")).toString();
    }
    
    
    /**
     * 获得数据范围ID
     * 
     * @param moduleId
     * @return
     */
    public static String getUsers(Users user, String moduleId) {
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, user.getId());
        StringBuffer sb = new StringBuffer();
        HashMap<String, String> users = roleCache.getModuleUsers();
        if (users != null&&users.size()>0) {
            String userIds = users.get(moduleId);
            if (Validate.notNull(userIds)) {
                String[] spId = userIds.split(",");
                for (String id : spId) {
                	if (Validate.notNull(id) && !LoginBean.getLoginBean().getUser().getId().equals(id)) {
                        sb.append("'");
                        sb.append(id);
                        sb.append("',");
                    }
                }
            }
        }

        sb.append("'");
        sb.append(LoginBean.getLoginBean().getUser().getId());
        sb.append("'");

        return sb.toString();
    }
    
    /**
     * 加载部门权限的ID集合
     * @param moduleId
     * @return
     */
    public static String getDepartments(Users user, String moduleId) {
    	RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, user.getId());
        HashMap<String, String> departments = roleCache.getModuleDepartments();
        StringBuffer sb = new StringBuffer();
        if(departments != null) {
        	String departmentIds = departments.get(moduleId);
        	if(Validate.notNull(departmentIds)) {
        		String[] spId = departmentIds.split(",");
        		for (String id : spId) {
        			sb.append("'");
        			sb.append(id);
        			sb.append("',");
				}
        	}
        }
        sb.append("'");
        sb.append("1");
        sb.append("'");
        return sb.toString();
    }
    
    public static String getDepartments(String moduleId) {
        return getDepartments(LoginBean.getLoginBean().getUser(),moduleId);
    }

    /**
     * 获得字段验证规则
     * @param webId
     * @return
     */
    public static String getRule(String webId) {
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, LoginBean.getLoginBean().getUser().getId());
        HashMap<String, String> fieldRule = roleCache.getModuleFields();
        if (fieldRule != null) {
            String rules = fieldRule.get(webId);
            if (Validate.notNull(rules))
                return rules;
            else
                return "[]";
        } else
            return "[]";
    }
    
    /**
     * 把List<String> 转换为 sql 子句
     */
    public static String idsToSQLSubString(List<String> ids){
    	if(!Validate.collectionNotNull(ids))
    		return "";
		StringBuffer sb = new StringBuffer();
		int size = ids.size();
		sb.append("(");
		for(int i=0;i<size;i++){
			sb.append("'");
			sb.append(ids.get(i));
			if(i!=size-1)
			sb.append("',");
			else 
			sb.append("'");	
		}
		sb.append(")");
		return sb.toString();
    }
   
    /**
	 * 获得总行数
	 * @param sql
	 * @param prefix
	 * @return
	 */
	public static String getRowCountSQL(String prefix,String sql){
		if(Validate.notNull(sql)&&sql.contains("from")){
			if(Validate.notNull(prefix))
			return prefix + sql.substring(sql.lastIndexOf("from"));
			else 
			return SQLConstant.SELECT_ROWCOUNT_PREFIX + sql.substring(sql.lastIndexOf("from"));
		}else {
			return prefix;
		}
	}
	
	public static String getRowCountSQL(String sql){
		return getRowCountSQL(null,sql);
	}
	
	/**
	 * 将分页数据添加到参数数组中
	 */
	public static Object[] addPageToParameters(Object[] args,Page page){
		if(args==null){
			args = new Object[]{(page.getCurrPage()-1)*page.getPageSize(),page.getPageSize()};
			return args;
		}else {
			Object[] newArgs = new Object[args.length+2];
			for(int i=0;i<args.length;i++){
				newArgs[i] = args[i];
			}
			newArgs[newArgs.length-2] = (page.getCurrPage()-1)*page.getPageSize();
			newArgs[newArgs.length-1] = page.getPageSize();
			return newArgs;
		}
	}
}