package com.ampletec.commons.net;

import com.ampletec.commons.lang.ByteArrayUtils;
import com.ampletec.commons.lang.ByteTypeSize;
import com.ampletec.commons.lang.TypeConverter;

import java.util.List;

public class PackageOut extends AbstractPackage {

	public PackageOut(short type) {
		super(new byte[0]);
		m_iType = type;
		writeShort(PackageOut.HEADER_MARK);
		writeShort(0); // byteBuf length
		writeShort(0); // check sum
		writeShort(type);
	}
	
	public PackageOut() {
		super(new byte[0]);
	}

	public void commitChanges() {
		ByteArrayUtils.putShort(m_byteBuf, (short) m_byteBuf.length, PackageOut.PKG_LENGTH_INDEX);
		ByteArrayUtils.putShort(m_byteBuf, (short) calculateCheckSum(), PackageOut.PKG_CHECKSUM_INDEX);
	}

	public byte[] array() {
		return m_byteBuf;
	}

	protected void appendBytes(byte[] buf) {
		m_byteBuf = ByteArrayUtils.appendBytes(m_byteBuf, buf);
		m_bytesCursor += buf.length;
	}

	public void writeByte(byte value) {
		byte[] buf = { value };
		appendBytes(buf);
	}
	
	public void writeBytes(byte[] value) {
		writeInt(value.length);
		appendBytes(value);
	}

	public void writeBoolean(boolean value) {
		byte[] buf = new byte[1];
		buf[0] = (byte) (value ? 1 : 0);
		appendBytes(buf);
	}

	public void writeShort(int value) {
		writeShort((short) value);
	}

	public void writeShort(short value) {
		byte[] buf = new byte[ByteTypeSize.SHORT_SIZE];
		ByteArrayUtils.putShort(buf, value, 0);
		appendBytes(buf);
	}

	public void writeInt(int value) {
		byte[] buf = new byte[ByteTypeSize.INT_SIZE];
		ByteArrayUtils.putInt(buf, value, 0);
		appendBytes(buf);
	}

	public void writeLong(long value) {
		byte[] buf = new byte[ByteTypeSize.LONG_SIZE];
		ByteArrayUtils.putInt(buf, (int) (value >> 32), 0);
		ByteArrayUtils.putInt(buf, (int) (value), 4);
		appendBytes(buf);

	}

	public void writeFloat(float value) {
		byte[] buf = new byte[ByteTypeSize.FLOAT_SIZE];
		ByteArrayUtils.putFloat(buf, value, 0);
		appendBytes(buf);
	}

	public void writeDouble(double value) {
		writeLong(Double.doubleToLongBits(value));
	}

	public void writeUTF(String value) throws Throwable {
		byte[] bytes = TypeConverter.toBytes(value);
		writeShort(bytes.length);
		appendBytes(bytes);
	}

	public void writeUTFBytes(String value) throws Throwable {
		byte[] bytes = TypeConverter.toBytes(value);
		appendBytes(bytes);
	}

	public void writeUTFArray(String[] value) throws Throwable {
		short length = (short) (value == null ? 0 : value.length);
		writeShort(length);
		if (length > 0) {
			for (int i = 0; value != null && i < value.length; i++) {
				writeUTF(value[i]);
			}
		}
	}

	public void writeUTFList(List<String> value) throws Throwable {
		short length = (short) (value == null ? 0 : value.size());
		writeShort(length);
		if (length > 0) {
			for (int i = 0; value != null && i < value.size(); i++) {
				writeUTF(value.get(i));
			}
		}
	}

	public void writeDoubleArray(Double[] value) throws Throwable {
		short length = (short) (value == null ? 0 : value.length);
		writeShort(length);
		if (length > 0) {
			for (int i = 0; value != null && i < value.length; i++) {
				writeDouble(value[i]);
			}
		}
	}

	public void writeIntArray(Integer[] value) throws Throwable {
		short length = (short) (value == null ? 0 : value.length);
		writeShort(length);
		if (length > 0) {
			for (int i = 0; value != null && i < value.length; i++) {
				writeInt(value[i]);
			}
		}
	}

	public void writeLongArray(Long[] value) throws Throwable {
		short length = (short) (value == null ? 0 : value.length);
		writeShort(length);
		if (length > 0) {
			for (int i = 0; value != null && i < value.length; i++) {
				writeLong(value[i]);
			}
		}
	}
}