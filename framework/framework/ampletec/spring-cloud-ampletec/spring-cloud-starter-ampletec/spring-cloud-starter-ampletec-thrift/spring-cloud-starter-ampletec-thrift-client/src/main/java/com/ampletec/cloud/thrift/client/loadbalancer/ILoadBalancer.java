package com.ampletec.cloud.thrift.client.loadbalancer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.ampletec.cloud.thrift.client.common.ThriftServerNode;

public interface ILoadBalancer<T extends ThriftServerNode> {

    public T chooseServerNode(String key);

    public Map<String, LinkedHashSet<T>> getAllServerNodes();

    public Map<String, LinkedHashSet<T>> getRefreshedServerNodes();

    public List<T> getServerNodes(String key);

    public List<T> getRefreshedServerNodes(String key);

}
