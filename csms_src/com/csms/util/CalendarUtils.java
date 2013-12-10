package com.csms.util;

import java.util.Calendar;

public class CalendarUtils {
	
	//获得当前的小时
	public static int getHour(){
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	
	//获得周末
	public static int getWeekDay(){
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}
	
	
	public static boolean sendAble(String timeType,String ruleDay){
		boolean flag = false;
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		//判断周末是否合适“1111001”
		int weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		String week = ruleDay.substring(weekday-1, weekday);
		if(!"1".equals(week)) return false;
		//判断时间点是否合适
		//全天发送
		if("0".equals(timeType)){
			return true;
		}else if("1".equals(timeType)){
			//工作时间发送
			if(hour>=8&hour<=18){
				return true;
			}else {
				return false;
			}
		}else if("2".equals(timeType)){
			//非工作时间发送
			if((hour<24&&hour>18)||(hour>0&&hour<8)){
				return true;
			}else {
				return false;
			}
		}
		
		
		return flag;
	}
	
}
