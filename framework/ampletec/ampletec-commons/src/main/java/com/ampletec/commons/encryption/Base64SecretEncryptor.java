package com.ampletec.commons.encryption;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import com.ampletec.commons.lang.SplitCharacter;
import com.ampletec.commons.lang.TypeConverter;

public class Base64SecretEncryptor {

	public static final String SECRET_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public static String getSigKey(String... args) throws NoSuchAlgorithmException {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(args[i]).append(SplitCharacter.UNDERLINE);
		}
		String sig = MD5.sign(buffer.toString() + SECRET_KEY);
		return sig.substring(0, 3) + sig.substring(sig.length() - 3);
	}

	public static String encodeBase64(String... args) throws Exception {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(args[i]).append(SplitCharacter.UNDERLINE);
		}
		String sig = getSigKey(args);
		return Base64.encodeBase64String(TypeConverter.toBytes(buffer.toString() + sig));
	}

	public static String[] decodeBase64(String base64Str) throws Exception {
		String[] ret = null;
		String str = TypeConverter.toUTF8String(Base64.decodeBase64(base64Str));
		String args[] = str.split(SplitCharacter.UNDERLINE);
		if (args != null && args.length > 1) {
			String sig = args[args.length - 1];
			String[] params = new String[args.length - 1];
			System.arraycopy(args, 0, params, 0, params.length);
			if (sig.equals(getSigKey(params))) {
				ret = params;
			}
		}
		return ret;
	}
}
