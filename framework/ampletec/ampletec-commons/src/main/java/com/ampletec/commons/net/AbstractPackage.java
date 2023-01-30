package com.ampletec.commons.net;

import java.nio.ByteBuffer;

public abstract class AbstractPackage {

	public static final short HEADER_MARK = 0x71ab;

	public static final int PKG_LENGTH_INDEX = 2;
	public static final int PKG_CHECKSUM_INDEX = 4;
	public static final int PKG_CODE_INDEX = 6;

	public AbstractPackage(byte[] byteBuf) {
		m_byteBuf = byteBuf;
	}

	protected byte[] m_byteBuf;
	protected transient int m_bytesCursor = 0;
	protected short m_iType;

	public short getType() {
		return m_iType;
	}

	public short calculateCheckSum() {
		int val1 = 0x77;
		int i = 6;
		while (i < m_byteBuf.length)
			val1 += m_byteBuf[i++] & 0xff;
		return (short) ((val1) & 0x7F7F);
	}
	
	@Deprecated
	public short calculateCheckSum2() {
		int val1 = 0x77;
		int i = 6;
		while (i < m_byteBuf.length)
			val1 += m_byteBuf[i++];
		return (short) ((val1) & 0x7F7F);
	}
	
	public ByteBuffer buffer() {
		return ByteBuffer.wrap(m_byteBuf.clone());
	}
	
	public int size() {
		return m_byteBuf.length;
	}
}