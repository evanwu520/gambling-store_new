package com.ampletec.cloud.mybatis.dynamic;

/**
 * 定义数据源类型
 * 
 * @author john
 *
 */
public enum DatabaseType {
	MASTER("write"), SLAVER("read"), REPORT("report");

	DatabaseType(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DatabaseType{" + "name='" + name + '\'' + '}';
	}
}
