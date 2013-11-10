package com.csms.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CSMSStringConstant {

	private CSMSStringConstant(){}
	public static final String ss = "1231";
	
	public static final HashMap<String,String> CONTENT_STATE_TYPE = new HashMap<String,String>();
	public static final HashMap<String,String> RULE_STATE_TYPE = new HashMap<String,String>();
	public static final List<String> timeList = new ArrayList<String>();
	
	public static final String RULE_NODAY = "没有执行日期";
	public static final String RULE_ALLDAY = "";
	public static final String RULE_ALLTIME = "全天执行";
	public static final String[] RULE_DAYS = new String[]{"周一","周二","周三","周四","周五","周六","周日"};
	static {
		CONTENT_STATE_TYPE.put("0","<font color=\"blue\">审核中</font>");
		CONTENT_STATE_TYPE.put("1", "<font color=\"green\">审核通过</font>");
		CONTENT_STATE_TYPE.put("2", "<font color=\"red\">审核失败</font>");
		
		for(int i=0;i<24;i++){
			if(i<10)
				timeList.add("0"+i+":00");
			else
				timeList.add(i+":00");
		}
		
		RULE_STATE_TYPE.put("0", "开启");
		RULE_STATE_TYPE.put("1", "关闭");
	}
	

	
}
