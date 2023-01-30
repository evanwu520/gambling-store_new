package com.ampletec.boot.mybatis.dynamic.autoconfigure;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ampletec.boot.mybatis.dynamic.DatabaseType;
import com.ampletec.boot.mybatis.dynamic.DynamicDataSource;
import com.ampletec.boot.mybatis.dynamic.aop.DataSourceAspect;

import lombok.extern.slf4j.Slf4j;

/**
 * 2019-02-25
 * 
 * @author john
 *
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DynamicSourceConfiguration extends DataSourceTransactionManagerAutoConfiguration {

	@Autowired
	private Environment env;

//	@Autowired
//	private DataSourceProperties properties;
	
	@Bean
	public DataSourceAspect dataSourceAspect() {
		return new DataSourceAspect();
	}

	/**
	 * 通过Spring JDBC 快速创建 DataSource 参数格式
	 * spring.datasource.master.jdbcurl=jdbc:mysql://localhost:3306/charles_blog
	 * spring.datasource.master.username=root spring.datasource.master.password=root
	 * spring.datasource.master.driver-class-name=com.mysql.jdbc.Driver
	 *
	 * @return DataSource
	 */
	@Bean(name = "masterDataSource")
	@Qualifier("masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource masterDataSource() {
		log.info("Initializing masterDataSource");
		return DataSourceBuilder.create().build();
	}

	/**
	 * 手动创建DruidDataSource,通过DataSourceProperties 读取配置 参数格式
	 * spring.datasource.url=jdbc:mysql://localhost:3306/charles_blog
	 * spring.datasource.username=root spring.datasource.password=root
	 * spring.datasource.driver-class-name=com.mysql.jdbc.Driver
	 *
	 * @return DataSource
	 * @throws SQLException
	 */
	@Bean(name = "slaveDataSource")
	@Qualifier("slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaveDataSource() throws SQLException {
		log.info("Initializing slaveDataSource");
		return DataSourceBuilder.create().build();
	}

	/**
	 * 构造多数据源连接池 Master 数据源连接池采用 HikariDataSource Slave 数据源连接池采用 DruidDataSource
	 * 
	 * @param master
	 * @param slave
	 * @return
	 */
	@Bean
	@Primary
	public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource master,
			@Qualifier("slaveDataSource") DataSource slave) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DatabaseType.MASTER, master);
		targetDataSources.put(DatabaseType.SLAVER, slave);
		log.info("Initializing dynamicDataSource");
		DynamicDataSource dataSource = new DynamicDataSource();
		// 该方法是AbstractRoutingDataSource的方法
		dataSource.setTargetDataSources(targetDataSources);
		// 默认的datasource设置为master
		dataSource.setDefaultTargetDataSource(master);

		String read = env.getProperty("spring.datasource.read");
		dataSource.setMethodType(DatabaseType.SLAVER, read);

		String write = env.getProperty("spring.datasource.write");
		dataSource.setMethodType(DatabaseType.MASTER, write);
		
		return dataSource;
	}

//	@Bean
//	public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource,
//			@Qualifier("slaveDataSource") DataSource slaveDataSource) throws Exception {
//		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
//		fb.setDataSource(this.dataSource(masterDataSource, slaveDataSource));
//		fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
//		fb.setMapperLocations(
//				new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
//		return fb.getObject();
//	}

	@Bean
	@Autowired
	public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
		log.info("DataSource Transaction Manager Initialized");
		return new DataSourceTransactionManager(dataSource);
	}
}
