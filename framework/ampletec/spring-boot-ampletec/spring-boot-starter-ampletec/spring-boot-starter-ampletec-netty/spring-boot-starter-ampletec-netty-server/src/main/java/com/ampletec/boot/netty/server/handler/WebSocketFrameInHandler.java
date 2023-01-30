package com.ampletec.boot.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import com.ampletec.boot.netty.server.NettyRequestAdapter;
import com.ampletec.commons.lang.ByteArrayUtils;
import com.ampletec.commons.messaging.ResponseMessage;
import com.ampletec.commons.net.PackageIn;
import com.ampletec.commons.net.PackageOut;

@Slf4j
public class WebSocketFrameInHandler extends SimpleChannelInboundHandler<Object> {

	public WebSocketFrameInHandler(NettyRequestAdapter adapter) {
		nettyAdapter = adapter;
		_readOffset = 0;
		_writeOffset = 0;
		_byteBuffer = new byte[0];
		_headerTemp = new byte[4];
	}

	private final NettyRequestAdapter nettyAdapter;

	protected int _readOffset;
	protected int _writeOffset;
	protected byte[] _byteBuffer;
	protected byte[] _headerTemp;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {
			try {
				handleWebSocketFrame(ctx, (WebSocketFrame) msg);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Throwable {
		if (frame instanceof CloseWebSocketFrame) {
			nettyAdapter.handleClose(ctx.channel());
			return;
		}
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		if (frame instanceof BinaryWebSocketFrame) {
			ByteBuf msg = frame.content();
			byte[] buf = new byte[msg.readableBytes()];
			msg.readBytes(buf);
			_byteBuffer = ByteArrayUtils.appendBytes(_byteBuffer, buf);
			_writeOffset += buf.length;
			if (_writeOffset > 1) {
				_readOffset = 0;
				if (_byteBuffer.length >= PackageIn.HEADER_SIZE) {
					handleByteBuffer(ctx);
				}
			}
		}
	}

	protected void handleByteBuffer(ChannelHandlerContext ctx) {
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
				handlePackage(ctx, in);
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

	@SuppressWarnings("deprecation")
	protected void handlePackage(ChannelHandlerContext ctx, PackageIn in) {
		short calcChecksum = in.calculateCheckSum();
		short inChecksum = in.getCheckSum();
		if (inChecksum == calcChecksum || inChecksum == in.calculateCheckSum2()) {
			try {
				if (in.getType() == -1) {
					ctx.writeAndFlush(new ResponseMessage(in.getType()) {
						private static final long serialVersionUID = 1L;

						@Override
						protected void writePackage(PackageOut out) throws Throwable {

						}
					});
				} else {
					nettyAdapter.handleRequest(ctx.channel(), in);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {
			log.warn("Protocol({}) checksum not equals({},{})", in.getType(), inChecksum, calcChecksum);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		nettyAdapter.handleClose(ctx.channel());
		ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		nettyAdapter.handleClose(ctx.channel());
		super.channelInactive(ctx);
	}
}
