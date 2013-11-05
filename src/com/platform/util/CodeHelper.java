package com.platform.util;

/**
 * <p>程序名称：       CodeHelper.java</p>
 * <p>程序说明：       编号帮助类</p>
 * <p>版权信息：       Copyright 深圳市维远泰克科技有限公司</p>
 * <p>时间：          Jan 7, 2011 4:11:12 PM</p>	
 * 
 * @author：          Marker.King
 * @version：         Ver 0.1
 */
public class CodeHelper {
	
	/**
	 * 部门编号的每一分段长度，如为3则code将类似123-456
	 */
	public static int DEPARTMENT_CODE_LENGTH = 3;

	/**
	 * 获得0补齐
	 * @param length
	 * @return
	 */
	public static String getZeroCode(int length) {
		String fix = "";
		for(;length > 0; length--) {
			fix += "0";
		}
		return fix;
	}
	
	/**
	 * 根据父级code获得第一个code
	 * @param superCode
	 * @param length
	 * @return
	 */
	public static String getFirstCode(String superCode, int length) {
		if(Validate.notNull(superCode)) {
			return getNextCode(superCode + "-" + getZeroCode(length), length);
		} else {
			return getNextCode(getZeroCode(length), length);
		}
	}
	
	/**
	 * 获得下一个code
	 * @param code
	 * @param length
	 * @return
	 */
	public static String getNextCode(String code, int length) {
		StringBuilder sb = new StringBuilder();
		String[] codeSplit = code.split("-");
		String lastCode = codeSplit[codeSplit.length-1];
		Integer lastNumber = Integer.parseInt(lastCode) + 1;
		String nextLastCode = getZeroCode(length) + lastNumber;
		nextLastCode = nextLastCode.substring(nextLastCode.length() - length);
		for(int i = 0, arrayLength = (codeSplit.length - 1); i < arrayLength; i++) {
			sb.append(codeSplit[i]);
			sb.append("-");
		}
		sb.append(nextLastCode);
		return sb.toString();
	}
	
	/**
     * 在前面补零
     */
    public static String addZeroPrefix(String testStr, int strLength) {
        String str = testStr.trim();
        while (str.length() < strLength) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * 在前面补零
     */
    public static String addZeroPrefix(int testInt, int strLength) {
        String str = String.valueOf(testInt);
        while (str.length() < strLength) {
            str = "0" + str;
        }
        return str;
    }

	
}