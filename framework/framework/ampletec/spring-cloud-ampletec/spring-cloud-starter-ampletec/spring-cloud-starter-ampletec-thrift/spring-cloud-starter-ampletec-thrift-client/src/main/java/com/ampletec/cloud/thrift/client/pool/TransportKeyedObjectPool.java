package com.ampletec.cloud.thrift.client.pool;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.transport.TTransport;

import com.ampletec.cloud.thrift.client.common.ThriftServerNode;

public class TransportKeyedObjectPool extends GenericKeyedObjectPool<ThriftServerNode, TTransport> {

	public TransportKeyedObjectPool(TransportKeyedPooledObjectFactory factory) {
		super(factory);
	}

	public TransportKeyedObjectPool(TransportKeyedPooledObjectFactory factory, GenericKeyedObjectPoolConfig config) {
		super(factory, config);
	}

	@Override
	public TTransport borrowObject(ThriftServerNode key) throws Exception {
		return super.borrowObject(key);
	}

	@Override
	public void returnObject(ThriftServerNode key, TTransport obj) {
		super.returnObject(key, obj);
	}
}
