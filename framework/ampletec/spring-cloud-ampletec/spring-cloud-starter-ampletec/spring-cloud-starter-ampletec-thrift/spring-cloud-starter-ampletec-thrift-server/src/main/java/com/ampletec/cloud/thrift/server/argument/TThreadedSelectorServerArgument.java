package com.ampletec.cloud.thrift.server.argument;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.ampletec.cloud.thrift.server.exception.ThriftServerException;
import com.ampletec.cloud.thrift.server.processor.TRegisterProcessor;
import com.ampletec.cloud.thrift.server.processor.TRegisterProcessorFactory;
import com.ampletec.cloud.thrift.server.properties.TThreadedSelectorServerProperties;
import com.ampletec.cloud.thrift.server.properties.ThriftServerProperties;
import com.ampletec.cloud.thrift.server.wrapper.ThriftServiceWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TThreadedSelectorServerArgument extends TThreadedSelectorServer.Args {

    private Map<String, ThriftServiceWrapper> processorMap = new HashMap<>();

    public TThreadedSelectorServerArgument(List<ThriftServiceWrapper> serviceWrappers, ThriftServerProperties properties)
            throws TTransportException {
        super(new TNonblockingServerSocket(properties.getPort()));

        transportFactory(new TFastFramedTransport.Factory());
        protocolFactory(new TCompactProtocol.Factory());

        TThreadedSelectorServerProperties threadedSelectorProperties = properties.getThreadedSelector();

        selectorThreads(threadedSelectorProperties.getSelectorThreads());
        workerThreads(threadedSelectorProperties.getMinWorkerThreads());
        acceptQueueSizePerThread(properties.getWorkerQueueCapacity());

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
