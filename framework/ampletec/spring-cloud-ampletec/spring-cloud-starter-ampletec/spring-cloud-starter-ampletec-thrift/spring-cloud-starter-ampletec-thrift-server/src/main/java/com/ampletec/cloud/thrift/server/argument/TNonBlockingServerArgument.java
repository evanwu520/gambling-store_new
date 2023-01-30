package com.ampletec.cloud.thrift.server.argument;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.ampletec.cloud.thrift.server.exception.ThriftServerException;
import com.ampletec.cloud.thrift.server.processor.TRegisterProcessor;
import com.ampletec.cloud.thrift.server.processor.TRegisterProcessorFactory;
import com.ampletec.cloud.thrift.server.properties.ThriftServerProperties;
import com.ampletec.cloud.thrift.server.wrapper.ThriftServiceWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TNonBlockingServerArgument extends TNonblockingServer.Args {

    private Map<String, ThriftServiceWrapper> processorMap = new HashMap<>();

    public TNonBlockingServerArgument(List<ThriftServiceWrapper> serviceWrappers, ThriftServerProperties properties)
            throws TTransportException {
        super(new TNonblockingServerSocket(properties.getPort()));

        transportFactory(new TFastFramedTransport.Factory());
        protocolFactory(new TCompactProtocol.Factory());

        try {
            TRegisterProcessor registerProcessor = TRegisterProcessorFactory.registerProcessor(serviceWrappers);

            processorMap.clear();
            processorMap.putAll(registerProcessor.getProcessorMap());

            processor(registerProcessor);
        } catch (Exception e) {
            throw new ThriftServerException("Can not create multiplexed processor for " + serviceWrappers, e);
        }
    }


    public Map<String, ThriftServiceWrapper> getProcessorMap() {
        return processorMap;
    }
}
