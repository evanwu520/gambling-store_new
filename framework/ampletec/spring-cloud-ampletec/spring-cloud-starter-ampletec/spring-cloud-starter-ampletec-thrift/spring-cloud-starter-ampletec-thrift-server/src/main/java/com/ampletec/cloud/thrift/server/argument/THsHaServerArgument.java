package com.ampletec.cloud.thrift.server.argument;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.ampletec.cloud.thrift.server.exception.ThriftServerException;
import com.ampletec.cloud.thrift.server.processor.TRegisterProcessor;
import com.ampletec.cloud.thrift.server.processor.TRegisterProcessorFactory;
import com.ampletec.cloud.thrift.server.properties.THsHaServerProperties;
import com.ampletec.cloud.thrift.server.properties.ThriftServerProperties;
import com.ampletec.cloud.thrift.server.wrapper.ThriftServiceWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class THsHaServerArgument extends THsHaServer.Args {

    private Map<String, ThriftServiceWrapper> processorMap = new HashMap<>();

    public THsHaServerArgument(List<ThriftServiceWrapper> serviceWrappers, ThriftServerProperties properties)
            throws TTransportException {
        super(new TNonblockingServerSocket(properties.getPort()));

        transportFactory(new TFastFramedTransport.Factory());
        protocolFactory(new TCompactProtocol.Factory());

        THsHaServerProperties hsHaProperties = properties.getHsHa();
        minWorkerThreads(hsHaProperties.getMinWorkerThreads());
        maxWorkerThreads(hsHaProperties.getMaxWorkerThreads());

        executorService(createInvokerPool(properties));

        try {
            TRegisterProcessor registerProcessor = TRegisterProcessorFactory.registerProcessor(serviceWrappers);

            processorMap.clear();
            processorMap.putAll(registerProcessor.getProcessorMap());

            processor(registerProcessor);
        } catch (Exception e) {
            throw new ThriftServerException("Can not create multiplexed processor for " + serviceWrappers, e);
        }

    }

    private ExecutorService createInvokerPool(ThriftServerProperties properties) {
        THsHaServerProperties hsHaProperties = properties.getHsHa();

        return new ThreadPoolExecutor(
                hsHaProperties.getMinWorkerThreads(),
                hsHaProperties.getMaxWorkerThreads(),
                hsHaProperties.getKeepAlivedTime(), TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(properties.getWorkerQueueCapacity()));
    }

    public Map<String, ThriftServiceWrapper> getProcessorMap() {
        return processorMap;
    }
}
