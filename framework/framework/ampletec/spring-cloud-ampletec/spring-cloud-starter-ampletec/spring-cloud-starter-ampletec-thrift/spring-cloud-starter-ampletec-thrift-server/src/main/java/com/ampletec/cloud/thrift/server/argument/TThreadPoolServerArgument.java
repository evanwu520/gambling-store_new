package com.ampletec.cloud.thrift.server.argument;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.ampletec.cloud.thrift.server.exception.ThriftServerException;
import com.ampletec.cloud.thrift.server.processor.TRegisterProcessor;
import com.ampletec.cloud.thrift.server.processor.TRegisterProcessorFactory;
import com.ampletec.cloud.thrift.server.properties.TThreadPoolServerProperties;
import com.ampletec.cloud.thrift.server.properties.TThreadedSelectorServerProperties;
import com.ampletec.cloud.thrift.server.properties.ThriftServerProperties;
import com.ampletec.cloud.thrift.server.wrapper.ThriftServiceWrapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TThreadPoolServerArgument extends TThreadPoolServer.Args {

    private Map<String, ThriftServiceWrapper> processorMap = new HashMap<>();

    public TThreadPoolServerArgument(List<ThriftServiceWrapper> serviceWrappers, ThriftServerProperties properties)
            throws TTransportException, IOException {
        super(new TServerSocket(new ServerSocket(properties.getPort())));

        transportFactory(new TFastFramedTransport.Factory());
        protocolFactory(new TCompactProtocol.Factory());

        TThreadPoolServerProperties threadPoolProperties = properties.getThreadPool();

        minWorkerThreads(threadPoolProperties.getMinWorkerThreads());
        maxWorkerThreads(threadPoolProperties.getMaxWorkerThreads());
        requestTimeout(threadPoolProperties.getRequestTimeout());

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
        TThreadedSelectorServerProperties threadedSelectorProperties = properties.getThreadedSelector();

        return new ThreadPoolExecutor(
                threadedSelectorProperties.getMinWorkerThreads(),
                threadedSelectorProperties.getMaxWorkerThreads(),
                threadedSelectorProperties.getKeepAlivedTime(), TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(properties.getWorkerQueueCapacity()));
    }

    public Map<String, ThriftServiceWrapper> getProcessorMap() {
        return processorMap;
    }

}
