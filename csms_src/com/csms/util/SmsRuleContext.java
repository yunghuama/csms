package com.csms.util;

import java.util.HashMap;

import com.csms.domain.SmsRule;

public class SmsRuleContext {

	//SmsRule 容器
	public static HashMap<String,SmsRule> contextMap = new HashMap<String,SmsRule>();

	public static HashMap<String, SmsRule> getContextMap() {
		return contextMap;
	}

	public static void setContextMap(HashMap<String, SmsRule> contextMap) {
		SmsRuleContext.contextMap = contextMap;
	}
	
	
	public static void addSmsRule(String number,SmsRule smsRule){
		SmsRuleContext.contextMap.put(number, smsRule);
	}
	
	public static void removeSmsRule(String number){
		SmsRuleContext.contextMap.remove(number);
	}
	
	public static SmsRule getSmsRule(String number){
		return SmsRuleContext.contextMap.get(number);
	}
}
