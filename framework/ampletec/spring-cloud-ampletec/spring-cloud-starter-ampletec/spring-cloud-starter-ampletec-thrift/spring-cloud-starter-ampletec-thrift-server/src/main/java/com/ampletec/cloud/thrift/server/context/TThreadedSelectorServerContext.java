package com.ampletec.cloud.thrift.server.context;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TTransportException;

import com.ampletec.cloud.thrift.server.argument.TThreadedSelectorServerArgument;
import com.ampletec.cloud.thrift.server.properties.ThriftServerProperties;
import com.ampletec.cloud.thrift.server.wrapper.ThriftServiceWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TThreadedSelectorServerContext implements ContextBuilder {

    private static TThreadedSelectorServerContext serverContext;
    private TThreadedSelectorServer.Args args;

    private TThreadedSelectorServerContext() {
    }

    public static TThreadedSelectorServerContext context() {
        if (Objects.isNull(serverContext)) {
            serverContext = new TThreadedSelectorServerContext();
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
            serverContext = (TThreadedSelectorServerContext) prepare();
            serverContext.args = new TThreadedSelectorServerArgument(serviceWrappers, properties);
        }

        if (Objects.nonNull(serverContext) && Objects.isNull(args)) {
            serverContext.args = new TThreadedSelectorServerArgument(serviceWrappers, properties);
        }

        return new TThreadedSelectorServer(serverContext.args);
    }

}
