package com.csms.util;

import com.platform.util.LoginBean;

public class LoginUtils {

	public static String depId;
	
	public static String getDepartmentId(){
		return LoginBean.getLoginBean().getUser().getDepartment().getId();
	}
	
	public static String getEnterpriseId(){
		return LoginBean.getLoginBean().getUser().getEnterprise().getId();
	}
}
