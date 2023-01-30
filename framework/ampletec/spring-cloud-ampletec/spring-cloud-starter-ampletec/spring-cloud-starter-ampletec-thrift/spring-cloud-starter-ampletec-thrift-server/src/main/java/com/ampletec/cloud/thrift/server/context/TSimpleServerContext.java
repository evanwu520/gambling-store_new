package com.ampletec.cloud.thrift.server.context;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TTransportException;

import com.ampletec.cloud.thrift.server.argument.TSimpleServerArgument;
import com.ampletec.cloud.thrift.server.properties.ThriftServerProperties;
import com.ampletec.cloud.thrift.server.wrapper.ThriftServiceWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TSimpleServerContext implements ContextBuilder {

    private static TSimpleServerContext serverContext;
    private TSimpleServer.Args args;

    private TSimpleServerContext() {
    }

    public static TSimpleServerContext context() {
        if (Objects.isNull(serverContext)) {
            serverContext = new TSimpleServerContext();
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
            serverContext = (TSimpleServerContext) prepare();
            serverContext.args = new TSimpleServerArgument(serviceWrappers, properties);
        }

        if (Objects.nonNull(serverContext) && Objects.isNull(args)) {
            serverContext.args = new TSimpleServerArgument(serviceWrappers, properties);
        }

        return new TSimpleServer(serverContext.args);
    }

}
