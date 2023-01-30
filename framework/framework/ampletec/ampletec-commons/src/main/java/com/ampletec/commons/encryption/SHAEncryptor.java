package com.ampletec.commons.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAEncryptor {

	public static String sha512(final String text) {
		return encrypt(text, "SHA-512");
	}

	private static String encrypt(final String text, final String type) {
		// 返回值
		String strResult = null;

		// 是否是有效字符串
		if (text != null && text.length() > 0) {
			try {
				// SHA 加密开始
				// 创建加密对象 并傳入加密類型
				MessageDigest messageDigest = MessageDigest.getInstance(type);
				// 传入要加密的字符串
				messageDigest.update(text.getBytes());
				// 得到 byte 類型结果
				byte byteBuffer[] = messageDigest.digest();

				// 將 byte 轉換爲 string
				StringBuffer strHexString = new StringBuffer();
				// 遍歷 byte buffer
				for (int i = 0; i < byteBuffer.length; i++) {
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1) {
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				// 得到返回結果
				strResult = strHexString.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		return strResult;
	}
}
