package com.ampletec.cloud.mybatis.dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * 使用DatabaseContextHolder获取当前线程的DatabaseType
 * 
 * @author john
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	public static final Map<DatabaseType, List<String>> METHOD_TYPE_MAP = new HashMap<DatabaseType, List<String>>();

	@Nullable
	@Override
	protected Object determineCurrentLookupKey() {
		DatabaseType type = DatabaseContextHolder.getDatabaseType();
		return type;
	}

	public void setMethodType(DatabaseType type, String content) {
		List<String> list = Arrays.asList(content.split(","));
		METHOD_TYPE_MAP.put(type, list);
	}
}
