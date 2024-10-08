package com.ampletec.cloud.thrift.server.context;

import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ampletec.cloud.thrift.server.properties.ThriftServerProperties;
import com.ampletec.cloud.thrift.server.wrapper.ThriftServiceWrapper;

import java.io.IOException;
import java.util.List;

public class ThriftServerContext extends AbstractThriftServerContext {

    private Logger log = LoggerFactory.getLogger(getClass());

    private TSimpleServerContext simpleServerContext;
    private TNonBlockingServerContext nonBlockingServerContext;
    private TThreadPoolServerContext threadedPoolServerContext;
    private THsHaServerContext hsHaServerContext;
    private TThreadedSelectorServerContext threadedSelectorServerContext;

    public ThriftServerContext(ThriftServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {
        this.properties = properties;
        this.serviceWrappers = serviceWrappers;

        contextInitializing();
    }

    private void contextInitializing() {
        this.simpleServerContext = TSimpleServerContext.context();
        this.nonBlockingServerContext = TNonBlockingServerContext.context();
        this.threadedPoolServerContext = TThreadPoolServerContext.context();
        this.hsHaServerContext = THsHaServerContext.context();
        this.threadedSelectorServerContext = TThreadedSelectorServerContext.context();
    }

    @Override
    protected TServer buildTSimpleServer() throws TTransportException, IOException {
        log.info("Build thrift server from SimpleServerContext");
        return simpleServerContext.buildThriftServer(properties, serviceWrappers);
    }

    @Override
    protected TServer buildTNonBlockingServer() throws TTransportException, IOException {
        log.info("Build thrift server from NonBlockingServerContext");
        return nonBlockingServerContext.buildThriftServer(properties, serviceWrappers);
    }

    @Override
    protected TServer buildTThreadPoolServer() throws TTransportException, IOException {
        log.info("Build thrift server from ThreadedPoolServerContext");
        return threadedPoolServerContext.buildThriftServer(properties, serviceWrappers);
    }

    @Override
    protected TServer buildTHsHaServer() throws TTransportException, IOException {
        log.info("Build thrift server from HsHaServerContext");
        return hsHaServerContext.buildThriftServer(properties, serviceWrappers);
    }

    @Override
    protected TServer buildTThreadedSelectorServer() throws TTransportException, IOException {
        log.debug("Build thrift server from ThreadedSelectorServerContext");
        return threadedSelectorServerContext.buildThriftServer(properties, serviceWrappers);
    }

}
