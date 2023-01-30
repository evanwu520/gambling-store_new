package com.ampletec.boot.netty.server.handler;

import com.ampletec.boot.netty.server.Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

public class WebSocketHttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
 
    public WebSocketHttpRequestHandler() {
        super();
    }
 
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {	
    	Client.setRemoteAddr(ctx.channel(), getRealIpAddr(req));
    	ctx.fireChannelRead(req.retain());
    }
    
    public static String getRealIpAddr(FullHttpRequest req) {  
        String ip = req.headers().get("x-forwarded-for");  
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {  
            ip = req.headers().get("Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {  
            ip = req.headers().get("WL-Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {  
            ip = req.headers().get("X-Real-IP");  
        }  
        return ip;  
     }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace(System.err);
    }
}
