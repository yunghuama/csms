package com.platform.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统日志常量
 * 
 * @author MarkerKing
 *
 */
public final class SyslogConstant {
	private SyslogConstant(){}
	
	public static final String SAVE = "新建";
	public static final String UPDATE = "修改";
	public static final String FIND = "查询";
	public static final String DELETE = "删除";
	public static final String LOGIN = "登入";
	public static final String LOGOUT = "登出";

	public static final List<String> OP_SELECT = new ArrayList<String>();

	static {
		OP_SELECT.add(SyslogConstant.SAVE);
		OP_SELECT.add(SyslogConstant.UPDATE);
		OP_SELECT.add(SyslogConstant.FIND);
		OP_SELECT.add(SyslogConstant.DELETE);
		OP_SELECT.add(SyslogConstant.LOGIN);
		OP_SELECT.add(SyslogConstant.LOGOUT);
	}
}