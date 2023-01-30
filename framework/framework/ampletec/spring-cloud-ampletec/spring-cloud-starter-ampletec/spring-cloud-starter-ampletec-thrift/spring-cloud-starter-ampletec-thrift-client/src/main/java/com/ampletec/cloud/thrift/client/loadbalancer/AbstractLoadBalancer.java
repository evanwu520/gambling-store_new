package com.ampletec.cloud.thrift.client.loadbalancer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.ampletec.cloud.thrift.client.discovery.ThriftConsulServerNode;
import com.ampletec.cloud.thrift.client.discovery.ThriftConsulServerNodeList;

public abstract class AbstractLoadBalancer implements ILoadBalancer<ThriftConsulServerNode> {

    public abstract ThriftConsulServerNode chooseServerNode(String key);

    @Override
    public Map<String, LinkedHashSet<ThriftConsulServerNode>> getAllServerNodes() {
        return getThriftServerNodeList().getServerNodeMap();
    }

    @Override
    public Map<String, LinkedHashSet<ThriftConsulServerNode>> getRefreshedServerNodes() {
        return getThriftServerNodeList().refreshThriftServers();
    }

    @Override
    public List<ThriftConsulServerNode> getServerNodes(String key) {
        return getThriftServerNodeList().getThriftServer(key);
    }

    @Override
    public List<ThriftConsulServerNode> getRefreshedServerNodes(String key) {
        return getThriftServerNodeList().refreshThriftServer(key);
    }

    public abstract ThriftConsulServerNodeList getThriftServerNodeList();

}
