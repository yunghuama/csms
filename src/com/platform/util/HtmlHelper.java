package com.platform.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 程序名称： HtmlHelper.java
 * </p>
 * <p>
 * 程序说明： 用于转换HTML
 * </p>
 * <p>
 * 版权信息： Copyright 深圳市维远泰克科技有限公司
 * </p>
 * <p>
 * 时间： Nov 25, 2010 10:44:34 AM
 * </p>
 * 
 * @author： Marker
 * @version： Ver 0.1
 */
public class HtmlHelper {
	
	/**
	 * 删除HTML标记 
	 * 
	 * @param element
	 * @return
	 */
	public static String deleteHtmlMark(String element) {

		if (null == element || "".equals(element.trim())) {
			return element;
		}

		Pattern pattern = Pattern.compile("<[^<|^>]*>");
		Matcher matcher = pattern.matcher(element);
		StringBuffer txt = new StringBuffer();

		while (matcher.find()) {
			String group = matcher.group();
			if (group.matches("<[\\s]*>")) {
				matcher.appendReplacement(txt, group);
			} else {
				matcher.appendReplacement(txt, "");
			}
		}
		matcher.appendTail(txt);
		// 替换各种HTML转义字符
		String result = txt.toString().trim();
		result = result.replaceAll("&nbsp;", " ");
		result = result.replaceAll("&ldquo;", "“");
		result = result.replaceAll("&rdquo;", "”");
		result = result.replaceAll("&amp;", "&");
		result = result.replaceAll("&lt;", "<");
		result = result.replaceAll("&gt;", ">");
		result = result.replaceAll("quot;", "\"");
		result = result.replaceAll("&copy;", "");
		result = result.replaceAll("&quot;", "");

		return result;
	}
	
	public static String changeHtmlMark(String element) {

		if (null == element || "".equals(element.trim())) {
			return element;
		}

		// 替换各种HTML转义字符
		String result = element.toString().trim();
		result = result.replaceAll("&", "&amp;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");

		return result;
	}

	/**
	 * HTML转换成文字
	 * 
	 * @param inputString
	 * @return
	 */
	public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>}
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>}
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}
}
