package com.ampletec.cloud.thrift.client.pool;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ampletec.cloud.thrift.client.common.ThriftServerNode;
import com.ampletec.cloud.thrift.client.exception.ThriftClientConfigException;
import com.ampletec.cloud.thrift.client.exception.ThriftClientOpenException;
import com.ampletec.cloud.thrift.client.properties.ThriftClientPoolProperties;
import com.ampletec.cloud.thrift.client.properties.ThriftClientProperties;

import java.util.Objects;

public class TransportKeyedPooledObjectFactory extends BaseKeyedPooledObjectFactory<ThriftServerNode, TTransport> {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ThriftClientProperties properties;

    public TransportKeyedPooledObjectFactory(ThriftClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public TTransport create(ThriftServerNode key) throws Exception {
        if (StringUtils.isBlank(key.getHost())) {
            throw new ThriftClientConfigException("Invalid Thrift server, node IP address: " + key.getHost());
        }

        if (key.getPort() <= 0 || key.getPort() > 65535) {
            throw new ThriftClientConfigException("Invalid Thrift server, node port: " + key.getPort());
        }

        TTransport transport;

        ThriftClientPoolProperties poolProperties = properties.getPool();
        if (Objects.isNull(poolProperties)) {
            transport = ThriftTransportFactory.determineTTranport(properties.getServiceModel(), key);

        } else {
            int connectTimeout = poolProperties.getConnectTimeout();
            if (connectTimeout > 0) {
                transport = ThriftTransportFactory.determineTTranport(properties.getServiceModel(), key, connectTimeout);
            } else {
                transport = ThriftTransportFactory.determineTTranport(properties.getServiceModel(), key);
            }
        }

        try {
            transport.open();
            log.info("Open a new transport {}", transport);
        } catch (TTransportException e) {
            throw new ThriftClientOpenException("Connect to " + key.getHost() + ":" + key.getPort() + " failed", e);
        }
        return transport;
    }

    @Override
    public PooledObject<TTransport> wrap(TTransport value) {
        return new DefaultPooledObject<>(value);
    }

    @Override
    public boolean validateObject(ThriftServerNode key, PooledObject<TTransport> value) {
        if (Objects.isNull(value)) {
            log.warn("PooledObject is already null");
            return false;
        }

        TTransport transport = value.getObject();
        if (Objects.isNull(transport)) {
            log.warn("Pooled transport is already null");
            return false;
        }

        try {
            return transport.isOpen();
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            return false;
        }
    }

    @Override
    public void destroyObject(ThriftServerNode key, PooledObject<TTransport> value) throws Exception {
        if (Objects.nonNull(value)) {
            TTransport transport = value.getObject();
            if (Objects.nonNull(transport)) {
                transport.close();
            }
            value.markAbandoned();
        }
    }

}
