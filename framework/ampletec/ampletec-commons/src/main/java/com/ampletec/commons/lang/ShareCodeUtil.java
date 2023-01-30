package com.ampletec.commons.lang;

public class ShareCodeUtil {

	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f',
			'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };

	private static final char[] repair = new char[] { 'X', 'T', '1', '8', '3', 's' };

	/** (不能与自定义进制有重复) */
	private static final char b = 'o';

	/** 进制长度 */
	private static final int binLen = r.length;

	/** 序列最小长度 */
	private static final int s = 6;

	/**
	 * 根据ID生成六位随机码
	 * 
	 * @param id
	 *            ID
	 * @return 随机码
	 */
	public static String toSerialCode(long id) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s - str.length() - 1; i++) {
				sb.append(repair[i]);
			}
			sb.append(b);
			str = sb.toString() + str;
		}
		return str;
	}

	public static long codeToId(String code) {
		char chs[] = code.toCharArray();
		long res = 0L;
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] == b) {
				res = 0L;
				continue;
			}
			int ind = 0;
			for (int j = 0; j < binLen; j++) {
				if (chs[i] == r[j]) {
					ind = j;
					break;
				}
			}
			if (i > 0) {
				res = res * binLen + ind;
			} else {
				res = ind;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		long userid = 1000001;
//		System.out.println();
//		String code = ShareCodeUtil.toSerialCode(userid);
//		System.out.println(code);
		userid = ShareCodeUtil.codeToId("XToepj");
		System.out.println(userid);

	}

}
