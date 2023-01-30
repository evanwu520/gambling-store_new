package com.ampletec.commons.lang;


public class ByteArrayUtils {

	public static short calculateCheckSum(byte[] bytes, int index) {
		int val1 = 0x77;
		while (index < bytes.length) val1 += bytes[index ++] & 0xff;;
		return (short) ((val1) & 0x7F7F);
	}

	public static byte[] appendBytes(byte[] dest, byte[] buf) {
		byte[] tmp = new byte[dest.length + buf.length];
		System.arraycopy(dest, 0, tmp, 0, dest.length);
		System.arraycopy(buf, 0, tmp, dest.length, buf.length);
		dest = tmp;
		return dest;
	}

	public static byte[] getBytes(byte[] bytes, int offset, int iLength) {
		byte[] retBytes = new byte[iLength];
		System.arraycopy(bytes, offset, retBytes, 0, iLength);
		return retBytes;
	}

	/**
	 * 转换short为byte
	 * 
	 * @param b
	 * @param s
	 *            需要转换的short
	 * @param index
	 */
	public static void putShort(byte b[], short s, int index) {
		b[index + 0] = (byte) (s >> 8);
		b[index + 1] = (byte) (s >> 0);
	}

	/**
	 * 通过byte数组取到short
	 * 
	 * @param b
	 * @param index
	 *            第几位开始取
	 * @return
	 */
	public static short getShort(byte[] b, int index) {
		return (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));
		// return (short)(((b[index + 0] & 0x00FF) << 8) | (0x00FF & b[index +
		// 1]));
	}

	public static void main(String[] args) throws Exception {
		byte[] buf = new byte[4];
		ByteArrayUtils.putShort(buf, (short) 0x71ab, 0);

		System.out.println(ByteArrayUtils.getShort(buf, 0));
	}

	/**
	 * 转换int为byte数组
	 * 
	 * @param b
	 * @param x
	 * @param i
	 */
	public static void putInt(byte[] b, int x, int i) {
		b[i] = (byte) (x >> 24);
		b[i + 1] = (byte) (x >> 16);
		b[i + 2] = (byte) (x >> 8);
		b[i + 3] = (byte) x;
	}

	/**
	 * 通过byte数组取到int
	 * 
	 * @param bb
	 * @param index
	 *            第几位开始
	 * @return
	 */
	public static int getInt(byte[] bytes, int iCursor) {
		// return (int) ((((bb[index + 3] & 0xff) << 24)
		// | ((bb[index + 2] & 0xff) << 16)
		// | ((bb[index + 1] & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));
		return bytes[iCursor + 3] & 0x000000ff | ((bytes[iCursor + 2] << 8) & 0x0000ff00) | ((bytes[iCursor + 1] << 16) & 0x00ff0000)
				| ((bytes[iCursor] << 24) & 0xff000000);
	}

	/**
	 * 转换long型为byte数组
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putLong(byte[] bb, long x, int index) {
		bb[index + 7] = (byte) (x >> 56);
		bb[index + 6] = (byte) (x >> 48);
		bb[index + 5] = (byte) (x >> 40);
		bb[index + 4] = (byte) (x >> 32);
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 0] = (byte) (x >> 0);
	}

	/**
	 * 通过byte数组取到long
	 * 
	 * @param bb
	 * @param index
	 * @return
	 */
	public static long getLong(byte[] bb, int index) {
		return ((((long) bb[index + 7] & 0xff) << 56) | (((long) bb[index + 6] & 0xff) << 48) | (((long) bb[index + 5] & 0xff) << 40)
				| (((long) bb[index + 4] & 0xff) << 32) | (((long) bb[index + 3] & 0xff) << 24) | (((long) bb[index + 2] & 0xff) << 16)
				| (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
	}

	/**
	 * 字符到字节转换
	 * 
	 * @param ch
	 * @return
	 */
	public static void putChar(byte[] bb, char ch, int index) {
		int temp = (int) ch;
		// byte[] b = new byte[2];
		for (int i = 0; i < 2; i++) {
			bb[index + i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
	}

	/**
	 * 字节到字符转换
	 * 
	 * @param b
	 * @return
	 */
	public static char getChar(byte[] b, int index) {
		int s = 0;
		if (b[index + 1] > 0)
			s += b[index + 1];
		else
			s += 256 + b[index + 0];
		s *= 256;
		if (b[index + 0] > 0)
			s += b[index + 1];
		else
			s += 256 + b[index + 0];
		char ch = (char) s;
		return ch;
	}

	/**
	 * float转换byte
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putFloat(byte[] bb, float x, int index) {
		// byte[] b = new byte[4];
		int l = Float.floatToIntBits(x);
		for (int i = 0; i < 4; i++) {
			bb[index + i] = new Integer(l).byteValue();
			l = l >> 8;
		}
	}

	/**
	 * 通过byte数组取得float
	 * 
	 * @param bb
	 * @param index
	 * @return
	 */
	public static float getFloat(byte[] b, int index) {
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}

	/**
	 * double转换byte
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putDouble(byte[] bb, double x, int index) {
		// byte[] b = new byte[8];
		long l = Double.doubleToLongBits(x);
		for (int i = 0; i < 4; i++) {
			bb[index + i] = new Long(l).byteValue();
			l = l >> 8;
		}
	}

	/**
	 * 通过byte数组取得float
	 * 
	 * @param bb
	 * @param index
	 * @return
	 */
	public static double getDouble(byte[] b, int index) {
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;
		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffl;
		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}
}
