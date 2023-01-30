package com.ampletec.commons.lang;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FixedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 6918023506928428613L;
	private int capacity = 500;//

	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();;

	public FixedLinkedHashMap(int capacity) {
		super(16, 0.75f, false);
		this.capacity = capacity;
	}

	public int getMaxEntries() {
		return capacity;
	}

	@Override
	public V get(Object key) {
		readWriteLock.readLock().lock();
		try {
			return super.get(key);
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	@Override
	public V put(K key, V value) {
		readWriteLock.writeLock().lock();
		try {
			return super.put(key, value);
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	/**
	 * 如果Map的尺寸大于设定的最大长度，返回true，再新加入对象时删除最老的对象
	 * 
	 * @param Map
	 *            .Entry eldest
	 * @return int
	 */
	@SuppressWarnings("rawtypes")
	protected boolean removeEldestEntry(Map.Entry eldest) {
		return size() > capacity;
	}
}
