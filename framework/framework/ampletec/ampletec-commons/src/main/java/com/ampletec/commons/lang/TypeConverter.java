package com.ampletec.commons.lang;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

public class TypeConverter {
	
	public static byte[] toBytes(long l) {
		byte[] uid = new byte[ByteTypeSize.LONG_SIZE];
		ByteArrayUtils.putLong(uid, l, 0);
		return uid;
	}

	public static byte[] toBytes(double d) {
		byte[] b = new byte[ByteTypeSize.DOUBLE_SIZE];
		ByteArrayUtils.putDouble(b, d, 0);
		return b;
	}
	
	public static byte[] toBytes(int i) {
		byte[] b = new byte[ByteTypeSize.INT_SIZE];
		ByteArrayUtils.putInt(b, i, 0);
		return b;
	}

	public static byte[] toBytes(String str) throws UnsupportedEncodingException {
		return str == null ? new byte[0] : str.getBytes("utf-8");
	}

	private static final Timestamp VALID_BEGIN_TIME = Timestamp.valueOf("2007-01-01 0:0:0");

	public static Timestamp toTimestamp(String src) {
		try {
			// return Timestamp.valueOf(src);
			Timestamp ts = Timestamp.valueOf(src);
			if (ts.before(VALID_BEGIN_TIME)) {
				return new Timestamp(System.currentTimeMillis());
			}
			return ts;
		} catch (Exception e) {
			return new Timestamp(System.currentTimeMillis());
		}
	}

	public static long toLong(byte[] l) {
		return ByteArrayUtils.getLong(l, 0);
	}

	public static String toUTF8String(byte[] bytes) throws UnsupportedEncodingException {
		return new String(bytes, "utf-8");
	}

	public static double toDouble(byte[] bb) {
		return ByteArrayUtils.getDouble(bb, 0);
	}

	public static int toInteger(byte[] bb) {
		return ByteArrayUtils.getInt(bb, 0);
	}

	public static int toInteger(String src) {
		try {
			if (null == src || src.trim().length() < 1)
				return 0; // xiaoyk 20081121
			return Integer.parseInt(src.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	public static byte toByte(String src) {
		try {
			if (null == src)
				return 0;
			return Byte.parseByte(src);
		} catch (Exception e) {
			return 0;
		}
	}

	public static short toShort(String src) {
		try {
			if (null == src)
				return 0;
			return Short.parseShort(src.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static float toFloat(String src) {
		try {
			if (null == src)
				return 0;
			return Float.parseFloat(src.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
