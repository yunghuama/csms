package com.platform.constants;

import java.util.HashMap;

/**
 * 字符串常量
 * 
 * @author MarkerKing
 *
 */
public final class StringConstant {
	private StringConstant(){}
	
	public static final String ROOT_ID = "00000000000000000000000000000000";
	public static final String ADMIN_ID = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String ALL = "ALL";
	public static final String SENDED = "S";

	public static final String USERS_ID = "402881c921f0fb2e0121f10c56aa0006";
	public static final String DICTIONARY_ID = "402881c921f0eed60121f0f13b560002";
	public static final String DEPARTMENT_ID = "402881c921f0fb2e0121f10954190001";
	public static final String INTERCOM_ID = "402881c921f0fb2e0121f111677b000c";
	public static final String SYSMESSAGE_ID = "402881c921f1146c0121f11954d60005";
	public static final String SYSLOG_ID = "402881c921f1e38e0121f1e642050001";
	public static final String ROLE_ID = "402881c921f1e38e0121f1eb06b90005";

	
	public static final String NO_LOGIN = "nologin";

	public static final HashMap<String, String> INPUT_SELECT_TYPE = new HashMap<String, String>();

	public static final HashMap<String, String> INTERCOM_SELECT = new HashMap<String, String>();

	public static final HashMap<String, String> NULLABLE = new HashMap<String, String>();
	
	public static final HashMap<String, String> DELETE_FLAG = new HashMap<String, String>();

	public static final HashMap<String, String> FIELD_TYPE = new HashMap<String, String>();

	public static final HashMap<String, String> SEX_RADIO = new HashMap<String, String>();

	public static final HashMap<String, String> STATE_RADIO = new HashMap<String, String>();
	public static final HashMap<String, String> AREA_RADIO = new HashMap<String, String>();
	
	public static final HashMap<String, String> INTERCOM_TYPE = new HashMap<String, String>();
	

	
	static {
		INPUT_SELECT_TYPE.put("T", "可填写");
		INPUT_SELECT_TYPE.put("F", "只能选择");

		NULLABLE.put("T", "是");
		NULLABLE.put("F", "否");
		
		DELETE_FLAG.put("T", "禁用");
		DELETE_FLAG.put("F", "启用");

		FIELD_TYPE.put("text", "文本");
		FIELD_TYPE.put("integer", "整数");
		FIELD_TYPE.put("decimal", "小数");
		FIELD_TYPE.put("datetime", "日期");
		FIELD_TYPE.put("linked", "关联");

		INTERCOM_SELECT.put("T", "<font color=\"green\">已读</font>");
		INTERCOM_SELECT.put("F", "<font color=\"red\">未读</font>");
		INTERCOM_SELECT.put("S", "<font color=\"blue\">已发送</font>");

		SEX_RADIO.put("0", "男");
		SEX_RADIO.put("1", "女");

		STATE_RADIO.put("T", "可用");
		STATE_RADIO.put("F", "不可用");
		
		AREA_RADIO.put("0", "深圳");
		AREA_RADIO.put("1", "济南");
		
		INTERCOM_TYPE.put("0", "内部通信");
		INTERCOM_TYPE.put("1", "流程提醒");
		INTERCOM_TYPE.put("2", "日志批注");
		
	}
}