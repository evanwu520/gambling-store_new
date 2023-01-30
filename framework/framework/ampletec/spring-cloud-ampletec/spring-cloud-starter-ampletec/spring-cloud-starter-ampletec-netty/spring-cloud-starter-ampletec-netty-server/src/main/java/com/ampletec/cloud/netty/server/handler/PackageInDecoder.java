package com.ampletec.cloud.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.ampletec.commons.lang.ByteArrayUtils;
import com.ampletec.commons.net.PackageIn;

@Slf4j
public class PackageInDecoder extends ByteArrayDecoder {

	public PackageInDecoder() {
		_readOffset = 0;
		_writeOffset = 0;
		_byteBuffer = new byte[0];
		_headerTemp = new byte[4];
	}

	protected int _readOffset;
	protected int _writeOffset;
	protected byte[] _byteBuffer;
	protected byte[] _headerTemp;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

		byte[] buf = new byte[msg.readableBytes()];
		msg.readBytes(buf);
		_byteBuffer = ByteArrayUtils.appendBytes(_byteBuffer, buf);
		_writeOffset += buf.length;
		if (_writeOffset > 1) {
			_readOffset = 0;
			if (_byteBuffer.length >= PackageIn.HEADER_SIZE) {
				handleByteBuffer(out);
			}
		}
	}

	protected void handleByteBuffer(List<Object> out) {
		int dataLeft = _writeOffset - _readOffset;
		do {
			int len = 0;
			while (_readOffset + 4 < _writeOffset) {
				_headerTemp[0] = _byteBuffer[_readOffset];
				_headerTemp[1] = _byteBuffer[_readOffset + 1];
				_headerTemp[2] = _byteBuffer[_readOffset + 2];
				_headerTemp[3] = _byteBuffer[_readOffset + 3];
				if (ByteArrayUtils.getShort(_headerTemp, 0) == PackageIn.HEADER_MARK) {
					len = ByteArrayUtils.getShort(_headerTemp, 2);
					break;
				} else {
					_readOffset++;
				}
			}

			dataLeft = _writeOffset - _readOffset;
			if (dataLeft >= len && len != 0) {
				PackageIn in = new PackageIn(ByteArrayUtils.getBytes(_byteBuffer, _readOffset, len));
				_readOffset += len;
				dataLeft = _writeOffset - _readOffset;
				handlePackage(out, in);
			} else {
				break;
			}
		} while (dataLeft >= PackageIn.HEADER_SIZE);

		if (dataLeft > 0) {
			// 临时存取未解析完成的字节
			_byteBuffer = ByteArrayUtils.getBytes(_byteBuffer, _readOffset, dataLeft);
		} else
			_byteBuffer = new byte[0];
		_readOffset = 0;
		_writeOffset = dataLeft;
	}

	protected void handlePackage(List<Object> out, PackageIn in) {
		short checksum = in.calculateCheckSum();
		if (in.getCheckSum() == checksum) {
			out.add(in);
		} else {
			log.warn("{} checksum not equals({},{})", in.getType(), in.getCheckSum(), checksum);
		}
	}
}