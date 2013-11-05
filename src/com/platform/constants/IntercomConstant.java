package com.platform.constants;

import java.util.HashMap;
import java.util.Map;

public final class IntercomConstant {
	private IntercomConstant(){}

	public static final String HasRead = "T";
	public static final String NoRead = "F";
	public static final String HasSend = "S";
	public static final String HasDeleted = "D";

	public static Map<String, String> READFLAG_TYPE = new HashMap<String, String>();

	static {
		READFLAG_TYPE.put("F", "未读");
		READFLAG_TYPE.put("T", "已读");
		READFLAG_TYPE.put("S", "已发送");
		READFLAG_TYPE.put("D", "已删除");
	}
}