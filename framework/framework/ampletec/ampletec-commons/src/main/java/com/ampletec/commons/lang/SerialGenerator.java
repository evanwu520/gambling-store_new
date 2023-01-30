package com.ampletec.commons.lang;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialGenerator {

	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyMMddHHmm = "yyMMddHHmm";
	public static final String yyMMddHHmmss = "yyMMddHHmmss";
	public static final String yyMMddHHmmssSSS = "yyMMddHHmmssSSS";
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	/**
	 * 生成订单编号(线程安全)
	 * 
	 * @return
	 */
	public static synchronized long createSerialNo(String datePattern) {
		return Long.parseLong(new SimpleDateFormat(datePattern).format(new Date()));
	}

	public static synchronized int createSerialInt(String datePattern) {
		return Integer.parseInt(new SimpleDateFormat(datePattern).format(System.currentTimeMillis()));
	}

	public static synchronized String createSerial(String datePattern) {
		return new SimpleDateFormat(datePattern).format(System.currentTimeMillis());
	}

	public static String randomCharacter(int length) {
		// 字符源，可以根据需要删减
		String generateSource = "0123456789ABCDEFGHIGKLMNOPQRSTUVWXYZ";
		String rtnStr = "";
		for (int i = 0; i < length; i++) {
			// 循环随机获得当次字符，并移走选出的字符
			String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
			rtnStr += nowStr;
			generateSource = generateSource.replaceAll(nowStr, "");
		}
		return rtnStr;
	}
}
