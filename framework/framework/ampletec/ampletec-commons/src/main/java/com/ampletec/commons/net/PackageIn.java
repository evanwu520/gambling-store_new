package com.ampletec.commons.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.ampletec.commons.lang.ByteArrayUtils;
import com.ampletec.commons.lang.ByteTypeSize;

public class PackageIn extends AbstractPackage {

	public static final int HEADER_SIZE = 8;

	public PackageIn(byte[] array) {
		super(array);
		m_capacity = m_byteBuf.length;
		m_iType = ByteArrayUtils.getShort(m_byteBuf, PackageIn.PKG_CODE_INDEX);
		m_bytesCursor = PackageIn.HEADER_SIZE;
	}

	protected final int m_capacity;

	public short getCheckSum() {
		return ByteArrayUtils.getShort(m_byteBuf, PackageIn.PKG_CHECKSUM_INDEX);
	}

	public byte readByte() {
		byte value = m_byteBuf[m_bytesCursor];
		m_bytesCursor += ByteTypeSize.BYTE_SIZE;
		return value;
	}

	public byte[] readBytes() {
		return this.readBytes(this.readInt());
	}

	public byte[] readBytes(int len) {
		byte[] value = ByteArrayUtils.getBytes(m_byteBuf, m_bytesCursor, len);
		m_bytesCursor += len;
		return value;
	}

	public short readShort() {
		short value = ByteArrayUtils.getShort(m_byteBuf, m_bytesCursor);
		m_bytesCursor += ByteTypeSize.SHORT_SIZE;
		return value;
	}

	public int readInt() {
		int value = ByteArrayUtils.getInt(m_byteBuf, m_bytesCursor);
		m_bytesCursor += ByteTypeSize.INT_SIZE;
		return value;
	}

	public long readLong() {
		return (((long) readInt()) << 32 & 0xffffffff00000000l) | (readInt() & 0x00000000ffffffffl);
	}

	public float readFloat() {
		float value = ByteArrayUtils.getFloat(m_byteBuf, m_bytesCursor);
		m_bytesCursor += ByteTypeSize.FLOAT_SIZE;
		return value;
	}

	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	public boolean readBoolean() {
		return readByte() == 1;
	}

	public Object readObject() throws IOException, ClassNotFoundException {
		short iLength = readShort();
		if (iLength > 0) {
			ObjectInputStream oIn = new ObjectInputStream(new ByteArrayInputStream(m_byteBuf, m_bytesCursor, iLength));
			m_bytesCursor += iLength;
			return oIn.readObject();
		}
		return null;
	}

	public String readUTF() throws IOException {
		return readUTFBytes(readShort());
	}

	public String readUTFBytes(int iLength) throws IOException {
		byte[] dst = ByteArrayUtils.getBytes(m_byteBuf, m_bytesCursor, iLength);
		m_bytesCursor += iLength;
		return new String(dst, "utf-8");
	}

	public Integer[] readIntArray() throws Throwable {
		Integer[] ret = null;
		short size = readShort();
		if (size > 0) {
			ret = new Integer[size];
			for (int i = 0; i < size; i++) {
				ret[i] = readInt();
			}
		}
		return ret;
	}

	public String[] readUTFArray() throws Throwable {
		String[] ret = null;
		short size = readShort();
		if (size > 0) {
			ret = new String[size];
			for (int i = 0; i < size; i++) {
				ret[i] = readUTF();
			}
		}
		return ret;
	}

	public Double[] readDoubleArray() throws Throwable {
		Double[] ret = null;
		short size = readShort();
		if (size > 0) {
			ret = new Double[size];
			for (int i = 0; i < size; i++) {
				ret[i] = readDouble();
			}
		}
		return ret;
	}

	public Long[] readLongArray() throws Throwable {
		Long[] ret = null;
		short size = readShort();
		if (size > 0) {
			ret = new Long[size];
			for (int i = 0; i < size; i++) {
				ret[i] = readLong();
			}
		}
		return ret;
	}
	
	public Boolean bytesAvailable() {
		return m_bytesCursor < m_capacity;
	}

	public static void main(String[] args) throws Exception {
		byte[] bytes = new byte[] { 113, -85, 0, 34, 1, 97, 0, 12, 0, 0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 4, 84, 73, 69, 68, 0, 1, 63, -16, 0, 0, 0, 0, 0, 0 };
		PackageIn in = new PackageIn(bytes);
		System.err.print(in.calculateCheckSum());
	}
}