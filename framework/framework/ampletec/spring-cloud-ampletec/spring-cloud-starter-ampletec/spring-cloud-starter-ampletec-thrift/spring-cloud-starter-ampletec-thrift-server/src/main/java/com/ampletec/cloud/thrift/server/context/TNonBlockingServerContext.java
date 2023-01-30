package com.ampletec.cloud.thrift.server.context;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;

import com.ampletec.cloud.thrift.server.argument.TNonBlockingServerArgument;
import com.ampletec.cloud.thrift.server.properties.ThriftServerProperties;
import com.ampletec.cloud.thrift.server.wrapper.ThriftServiceWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TNonBlockingServerContext implements ContextBuilder {

    private static TNonBlockingServerContext serverContext;
    private TNonblockingServer.Args args;

    private TNonBlockingServerContext() {
    }

    public static TNonBlockingServerContext context() {
        if (Objects.isNull(serverContext)) {
            serverContext = new TNonBlockingServerContext();
        }
        return serverContext;
    }

    @Override
    public ContextBuilder prepare() {
        return context();
    }

    @Override
    public TServer buildThriftServer(ThriftServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) throws TTransportException, IOException {
        if (Objects.isNull(serverContext)) {
            serverContext = (TNonBlockingServerContext) prepare();
            serverContext.args = new TNonBlockingServerArgument(serviceWrappers, properties);
        }

        if (Objects.nonNull(serverContext) && Objects.isNull(args)) {
            serverContext.args = new TNonBlockingServerArgument(serviceWrappers, properties);
        }

        return new TNonblockingServer(serverContext.args);
    }

}
