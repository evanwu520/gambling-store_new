package com.ampletec.boot.mybatis.dynamic;

/**
 * 保存一个线程安全的DatabaseType容器
 * 
 * @author john
 *
 */
public class DatabaseContextHolder {
	private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

	public static void setDatabaseType(DatabaseType type) {
		contextHolder.set(type);
	}

	public static DatabaseType getDatabaseType() {
		return contextHolder.get();
	}

	public static void remove() {
		contextHolder.remove();
	}

	public static void slave() {
    	setDatabaseType(DatabaseType.SLAVER);
        System.out.println("切换到slave");
	}
}
