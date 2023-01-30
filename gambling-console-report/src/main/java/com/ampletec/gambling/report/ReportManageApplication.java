package com.ampletec.gambling.report;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


//@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, ActiveMQAutoConfiguration.class, SessionAutoConfiguration.class })
@MapperScans(value = {
		@MapperScan(basePackages = "com.ampletec.gambling.report.mapper",
				sqlSessionFactoryRef = "sqlSessionFactory1",sqlSessionTemplateRef = "sqlSessionTemplate1")})
public class ReportManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportManageApplication.class, args);
	}
}
