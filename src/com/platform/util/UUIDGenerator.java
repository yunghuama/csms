package com.platform.util;

import java.io.Serializable;
import java.net.InetAddress;
/**
 * <p>
 *    说明: 从hibernate中提取的,用于产生32位随机主键的类
 * </p>
 *
 * 作者:马永华
 * 时间:2012-4-16 下午12:19:53
 * 版本:V1.0
 */
public class UUIDGenerator {
	private static final int IP;
	public static int IptoInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + bytes[i];
		}
		return result;
	}

	static {
		int ipadd;
		try {
			ipadd = IptoInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}
	
	private static short counter = (short) 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	protected static int getJVM() {
		return JVM;
	}

	protected static short getCount() {
		synchronized (UUIDGenerator.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	protected static int getIP() {
		return IP;
	}

	protected static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	private final static String sep = "";

	protected  static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	//直接调用该方法,即可产生32永不重复的ID
	public static String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(
				format(getJVM())).append(sep).append(format(getHiTime()))
				.append(sep).append(format(getLoTime())).append(sep).append(
						format(getCount())).toString();
	}
	
}
