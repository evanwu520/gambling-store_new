package com.ampletec.gambling.report.autoconfigure;

import com.ampletec.gambling.report.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//https://github.com/eugenp/tutorials/blob/master/spring-boot-modules/spring-boot-basic-customization/src/main/java/com/baeldung/bootcustomfilters/FilterConfig.java
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/API/*", "/Report/*","/Wager/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
