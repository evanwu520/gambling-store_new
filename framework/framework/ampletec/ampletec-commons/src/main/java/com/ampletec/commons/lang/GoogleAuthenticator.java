package com.ampletec.commons.lang;

import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

public class GoogleAuthenticator {
	
	public static void main(String args[]) throws Exception {
		String secret = GoogleAuthenticator.generateSecretKey();
		System.out.println(secret);
	}

	private static final int SECRET_SIZE = 10;

	private static final String SEED = "g8GjEvTbW5oVSV7avLBdwIHqGlUYNzKFI7izOF8GwLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";

	private static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

	int window_size = 1;

	public static Boolean authcode(String codes, String savedSecret) {
		long code = Long.parseLong(codes);
		long t = System.currentTimeMillis();
		GoogleAuthenticator ga = new GoogleAuthenticator();
		boolean r = ga.check_codeOnce(savedSecret, code, t);
		return r;
	}

	public static String genSecret(String user) throws Exception {
		String secret = GoogleAuthenticator.generateSecretKey();
		GoogleAuthenticator.genQRBarcodeURL(user, secret);
		return secret;
	}

	public static String generateSecretKey() throws Exception {
		SecureRandom sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
		sr.setSeed(Base64.decodeBase64(SEED));
		byte[] buffer = sr.generateSeed(SECRET_SIZE);
		Base32 codec = new Base32();
		byte[] bEncodedKey = codec.encode(buffer);
		String encodedKey = new String(bEncodedKey);
		return encodedKey;
	}

	public static String genQRBarcodeURL(String user, String secret) throws Exception {
		return URLEncoder.encode("otpauth://totp/" + user + "?secret=" + secret, "UTF-8");
	}

	public boolean check_codeOnce(String secret, long code, long timeMsec) {
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(secret);
		long t = (timeMsec / 1000L) / 30L;
		try {
			long hash = verify_code(decodedKey, t);
			if (hash == code) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return false;
	}

	public boolean check_code(String secret, long code, long timeMsec) {
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(secret);
		long t = (timeMsec / 1000L) / 30L;
		for (int i = -window_size; i <= window_size; ++i) {
			long hash;
			try {
				hash = verify_code(decodedKey, t + i);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			if (hash == code) {
				return true;
			}
		}
		return false;
	}

	private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}
		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);
		int offset = hash[20 - 1] & 0xF;
		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			truncatedHash |= (hash[offset + i] & 0xFF);
		}
		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;
		return (int) truncatedHash;
	}
}
