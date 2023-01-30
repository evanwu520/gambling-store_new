package com.ampletec.cloud.thrift.client.loadbalancer;

import com.ampletec.cloud.thrift.client.common.ThriftServerNode;

public interface IRule {

    public ThriftServerNode choose(String key);

    public void setLoadBalancer(ILoadBalancer lb);

    public ILoadBalancer getLoadBalancer();

}
