package com.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 文件名：DateTimeHelper.java<br>
 * 说明：用于日期时间的Form类 <br>
 * 作者：林安城<br>
 * 更改记录： <br>
 * -------------------------------------------------------<br>
 * 改动人 时间 原因<br>
 * -------------------------------------------------------<br>
 * 林安城 2007-7-10 创建文件<br>
 */
public class DateTimeHelper {

    private static int[] day = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public DateTimeHelper() {

    }

    /**
     * 时间格式：yyyyMMdd-HHmmss-SSS
     * 
     * @return
     */
    public static String getFullTime() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
        return fmt.format(date);
    }

    /**
     * 时间格式：yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */
    public static String getDateTime() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(date);
    }

    /**
     * 时间格式：yyyy-MM-dd
     * 
     * @return
     */
    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(date);
    }
    
    /**
     * 返回当前日期,格式: YYYYMMDD
     * @return
     */
    public static String getDateStr() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        cal = Calendar.getInstance();
        return simpleDateFormat.format(cal.getTime());
    }

    /**
     * 时间格式：
     * 
     * @return
     */
    public static Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 得到Date类型的当前时间，格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getDateTimeD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime()));
            return date;
        } catch (ParseException pe) {
            return null;
        }
    }
    /**
     * 得到Date类型的当前时间，格式yyyy-MM-dd
     * @return
     */
    public static Date getDateD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime()));
            return date;
        } catch (ParseException pe) {
            return null;
        }
    }


    /**
     * 格式化日期，成为一天的最大时刻点
     * 
     * @param date
     * @return
     */
    public static Date formatMaxDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);// 不能使用999精确度为百分之三秒
        return cal.getTime();
    }

    /**
     * 格式化日期，成为一天的最小时刻点
     * 
     * @param date
     * @return
     */
    public static Date formatMinDate(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getDateAndMonth(Date date) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
            return format.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 按格式进行统一时间
     * 
     * @param format
     * @param date
     * @return
     */
    public static String formatDateTime(String format, Date date) {
    	if(date!=null) {
    		SimpleDateFormat formater = new SimpleDateFormat(format);
            return formater.format(date);
    	} else {
    		return "";
    	}
    }

    /**
     * 比较时间，返回秒数
     * 
     * @param begin
     * @param end
     * @return
     */
    public static Long compare(Date begin, Date end) {
        long between = end.getTime() - begin.getTime();
        return new Long(between / 1000);

    }

    /**
     * 比较时间，返回天数
     * 
     * @param begin
     * @param end
     * @return
     */
    public static Long compare(Date date) {
        long between = new Date().getTime() - date.getTime();
        return between / (1000 * 60 * 60 * 24);
    }

    /**
     * 比较时间，返回大小
     * 
     * @param begin
     * @param end
     * @return
     */
    public static boolean greaterOrEqual(Date begin, Date end) {
        int result = begin.compareTo(end);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回本周在全年的第几周
     * 
     * @return
     */
    public static Integer getNowWeekOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 返回时间所在月的第几周
     * 
     * @param date
     * @return
     */
    public static Integer getWeekOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 根据年份和第几周取得开始日期和截止日期 周日～周六
     * 
     * @param year
     * @param weekOfYear
     * @return
     */
    public static List<Date> getDatesOfWeek(Integer year, Integer weekOfYear) {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR, 6);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 根据年份和第几周取得一个周的日期 周日～周六
     * 
     * @param year
     * @param weekOfYear
     * @return
     */
    public static List<Date> getAllDatesOfWeek(Integer year, Integer weekOfYear) {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        list.add(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        list.add(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        list.add(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        list.add(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        list.add(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        list.add(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }
    
    /**
     * 获得7天开始和结束
     * @return
     */
    @SuppressWarnings("deprecation")
	public static Date[] get7Day(Date date) {
    	Date[] dates = new Date[2];
    	Date before7Day = new Date();
    	before7Day.setHours(0);
    	before7Day.setMinutes(0);
    	before7Day.setSeconds(0);
    	before7Day.setDate(date.getDate() - 7);
    	before7Day.setMonth(date.getMonth());
    	before7Day.setYear(date.getYear());
    	dates[0] = before7Day;
    	
    	date.setHours(23);
    	date.setMinutes(59);
    	date.setSeconds(59);
    	dates[1] = date;
    	return dates;
    }

    /**
     * 取得今天的开始和截止时间
     * 
     * @return
     */
    public static List<Date> getDatesOfToday() {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 取得今天的开始和截止时间
     * 
     * @return
     */
    public static List<Date> getDatesOf(Date date) {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 取得最近n天的开始和截止时间
     * 
     * @return
     */
    public static List<Date> getDatesOfLatelyDays(int days) {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR, days - 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 根据当前时间取得本周的开始日期和截止日期 周日～周六
     * 
     * @param year
     * @param weekOfYear
     * @return
     */
    public static List<Date> getDatesOfNowWeek() {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR, 6);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 取得从最近算起的n-m天
     */
    public static List<Date> getDatesOfLatelyDays(int start, int end) {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        // cal.add(Calendar.DAY_OF_YEAR, -end+1);
        cal.add(Calendar.DAY_OF_YEAR, -end - 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        // cal.add(Calendar.DAY_OF_YEAR, end-start-1);
        cal.add(Calendar.DAY_OF_YEAR, end - start + 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 根据当前月份取得开始日期和截止日期 每月的第一天和最后一天
     * 
     * @param year
     * @param weekOfYear
     * @return
     */
    public static List<Date> getDatesOfNowMonth() {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        cal.set(Calendar.DATE, cal.getMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 根据年份和月份取得开始日期和截止日期 每月的第一天和最后一天
     * 
     * @param year
     * @param weekOfYear
     * @return
     */
    public static List<Date> getDatesOfMonth(Integer year, Integer monthOfYear) {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear - 1);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        cal.set(Calendar.DATE, cal.getMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 根据当前的年份和月份取得开始日期和截止日期 每月的第一天和最后一天
     * 
     * @param year
     * @param weekOfYear
     * @return
     */
    public static List<Date> getDatesOfMonth() {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        list.add(cal.getTime());
        cal.set(Calendar.DATE, cal.getMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 998);
        list.add(cal.getTime());
        return list;
    }

    /**
     * 返回本月在的第几月 使用1～12月份制
     * 
     * @return
     */
    public static Integer getNowMonthOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 阳历转农历 初一返回月份 节日返回传统节日名称
     * 
     * @param date
     * @return
     */
    public static String gregorianToLunar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        LunarCalendar lunar = new LunarCalendar(cal);
        return lunar.toString();
    }

    /**
     * 后退日期，可以为周、月
     * 
     * @param type
     *            MONTH or WEEK
     * @return
     */
    public static String backOffDate(String type) {
        Calendar cal = Calendar.getInstance();
        if (type.equals("MONTH")) {
            cal.add(Calendar.MONTH, -1);
        } else if (type.equals("WEEK")) {
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        }
        return formatDateTime("yyyy-MM-dd HH:mm:ss", cal.getTime());
    }

    /**
     * 后退日期，可以为周、月
     * 
     * @param type
     *            MONTH or WEEK
     * @return
     */
    public static Date backOffDateType(String type) {
        Calendar cal = Calendar.getInstance();
        if (type.equals("MONTH")) {
            cal.add(Calendar.MONTH, -1);
        } else if (type.equals("WEEK")) {
            cal.add(Calendar.WEEK_OF_YEAR, -1);
        }
        return cal.getTime();
    }

    @SuppressWarnings("deprecation")
    public static Date getMonthLast(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        Date _d = new Date();
        _d.setYear(year);
        _d.setMonth(month);
        if (month == 1) {
            if (0 == year % 4 && ((year % 100 != 0) || (year % 400 == 0)))
                _d.setDate(day[month] + 1);
        } else
            _d.setDate(day[month]);
        _d.setHours(23);
        _d.setMinutes(59);
        _d.setSeconds(59);
        return _d;
    }

    @SuppressWarnings("deprecation")
    public static Date getMonthFirst(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        Date _d = new Date();
        _d.setYear(year);
        _d.setMonth(month);
        _d.setDate(1);
        _d.setHours(0);
        _d.setMinutes(0);
        _d.setSeconds(0);
        return _d;
    }

    @SuppressWarnings("deprecation")
    public static Date getDayFirst(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int da = date.getDate();
        Date _d = new Date();
        _d.setYear(year);
        _d.setMonth(month);
        _d.setDate(da);
        _d.setHours(0);
        _d.setMinutes(0);
        _d.setSeconds(0);
        return _d;
    }

    @SuppressWarnings("deprecation")
    public static Date getDayLast(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int da = date.getDate();
        Date _d = new Date();
        _d.setYear(year);
        _d.setMonth(month);
        _d.setDate(da);
        _d.setHours(23);
        _d.setMinutes(59);
        _d.setSeconds(59);
        return _d;
    }

    @SuppressWarnings("deprecation")
    public static Date getEndTime(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int da = date.getDate();
        int hours = date.getHours();
        Date _d = new Date();
        _d.setYear(year);
        _d.setMonth(month);
        _d.setDate(da);
        _d.setHours(hours);
        if (date.getMinutes() == 0)
            _d.setMinutes(30);
        else
            _d.setMinutes(59);
        _d.setSeconds(0);
        return _d;
    }

    /**
     * xiasx 获取生日格式的字符串 MM-dd 如09-09
     * 
     * @param days
     *            从当前时间算起，向后延时的天数
     * @return bStr
     */
    @SuppressWarnings("deprecation")
    public static String getBirthday(int days) {
        String bStr = "";
        Date date = new Date();
        date.setDate(date.getDate() + days); // 延时的天数
        if ((date.getMonth() + 1) < 10) {
            bStr += "0" + (date.getMonth() + 1) + "-";
        } else {
            bStr += (date.getMonth() + 1) + "-";
        }
        if ((date.getDate()) < 10) {
            bStr += "0" + date.getDate();
        } else {
            bStr += date.getDate();
        }
        return bStr;
    }

    /**
     * xiasx 获取中文格式的日期字符串 如2008年9月9日
     * 
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getChineseDate() {
        String cStr = "";
        Date date = new Date();
        cStr = (date.getYear() + 1900) + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日";
        return cStr;
    }

    /**
     * xiasx 返回当前是星期几
     * 
     * @return
     */
    @SuppressWarnings( { "deprecation", "static-access" })
    public static String getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        LunarCalendar lunar = new LunarCalendar(cal);
        String dStr = "";
        Date date = new Date();
        if (date.getDay() == 0) {
            dStr = "日";
        } else {
            dStr = lunar.chineseNumber[date.getDay() - 1];
        }

        return dStr;
    }

    /**
     * xiasx 获取传统节日 如果不是传统节日则返回""
     * 
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getHolidayByLunar(Date date) {
        String str = "";
        // 节假日数组
        Map<String, String> hMap = new HashMap<String, String>();
        hMap.put("一月初一", "春节");
        hMap.put("一月十五", "元宵节");
        hMap.put("五月初五", "端午节");
        hMap.put("八月十五", "中秋节");
        hMap.put("九月初九", "重阳节");
        // 阳历转阴历
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        LunarCalendar lunar = new LunarCalendar(cal);
        // 格式为 五月十七
        String lStr = lunar.chineseNumber[lunar.getMonth() - 1] + "月" + lunar;
        for (Iterator iter = hMap.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            if (lStr.equals(key)) {
                str += hMap.get(key) + "(" + key + ")"; // 阴历节日名称
            }
        }
        return str; // 返回节日名称
    }

    /**
     * xiasx 获取阳历节日 不是则返回""
     * 
     * @return
     */
    public static String getHolidayByGregorian(String dt) {
        String str = "";
        // 节假日数组
        Map<String, String> hMap = new HashMap<String, String>();
        hMap.put("01-01", "元旦");
        hMap.put("02-14", "情人节");
        hMap.put("03-08", "妇女节");
        hMap.put("03-12", "植树节");
        hMap.put("05-01", "劳动节");
        hMap.put("05-04", "青年节");
        hMap.put("06-01", "儿童节");
        hMap.put("07-01", "中国共产党诞生日、香港回归纪念日");
        hMap.put("08-01", "建军节");
        hMap.put("09-10", "教师节");
        hMap.put("10-01", "国庆节");
        hMap.put("12-25", "圣诞节");
        for (Iterator iter = hMap.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            if (dt.equals(key)) {
                str += " " + hMap.get(key); // 阳历节日名称
            }
        }
        return str;
    }

    /**
     * 获取提醒时间
     * 
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date getRemindTime(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int da = date.getDate();
        Date _d = new Date();
        _d.setYear(year);
        _d.setMonth(month);
        _d.setDate(da);
        _d.setHours(16);
        _d.setMinutes(0);
        _d.setSeconds(0);
        return _d;
    }

    /**
     * 根据当前时间按设置自定义日期
     * 
     * @param days
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date setDifinitionDate(int days) {
        Date date = new Date();
        date.setDate(date.getDate() + days);
        return date;
    }

    /**
     * 根据自定义时间设置自定义日期
     * 
     * @param date
     * @param days
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date setDifinitionDate(Date date, int days) {
        Date date1 = new Date();
        int d = date.getDate();
        date1.setDate(d + days);
        return date1;
    }
    
    /**
     * 计算日期的天数加减
     * added by zxb in 2011-5-24
     */
    public static Date addOrSubDays(Date beginDate,int d){
    	long secs=beginDate.getTime();
		long aa=secs+d*24 * 3600 * 1000;
		Date myDate=new Date(aa);
		return myDate;
	}
    /**
     *  得到和当前日期相差days天的日期，如果days小于零则是以前的日期
     *  added by gj in 2011-8-19
     */
    public static String getApartDate(int days) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return sdf.format(cal.getTime());
    }   
    /**
     *  得到和指定日期相差days天的日期，如果days小于零则是以前的日期
     *  added by gj in 2011-8-19
     */
    public static String getApartDate(String dateStr, int days) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
        date.setTime(date.getTime() + days* 24 * 60 * 60 * 1000);
        return sdf.format(date);
    }
}