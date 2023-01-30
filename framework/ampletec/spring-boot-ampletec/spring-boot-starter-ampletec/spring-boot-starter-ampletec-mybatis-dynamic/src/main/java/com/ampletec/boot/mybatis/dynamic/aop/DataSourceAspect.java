package com.ampletec.boot.mybatis.dynamic.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.ampletec.boot.mybatis.dynamic.DatabaseContextHolder;
import com.ampletec.boot.mybatis.dynamic.DatabaseType;
import com.ampletec.boot.mybatis.dynamic.DynamicDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态处理数据源，根据命名区分
 * 
 * @author john
 *
 */
@Slf4j
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {

	@Pointcut("execution(* com.ampletec..*Mapper.*(..))")
	public void aspect() {}

	@Before("aspect()")
	public void before(JoinPoint point) {
		String method = point.getSignature().getName();
		try {
			for (DatabaseType type : DatabaseType.values()) {
				List<String> values = DynamicDataSource.METHOD_TYPE_MAP.get(type);
				for (String key : values) {
					if (method.startsWith(key)) {
						DatabaseContextHolder.setDatabaseType(type);
						if (log.isDebugEnabled())
							log.debug(">>{} used was {}<<", method, DatabaseContextHolder.getDatabaseType());
						return;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (log.isDebugEnabled())
			log.debug(">>{} used default master<<", method);
		DatabaseContextHolder.setDatabaseType(DatabaseType.MASTER);
	}

	@After("aspect()")
	public void after(JoinPoint point) {
		if (log.isDebugEnabled())
			log.debug("method name={},after key={}", point.getSignature().getName(), DatabaseContextHolder.getDatabaseType());
		// 避免多数据源事务问题，执行完成清理线程设置的类型
		DatabaseContextHolder.remove();
	}
}
