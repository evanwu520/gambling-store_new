package com.ampletec.gambling.report.autoconfigure;

import com.ampletec.gambling.report.filter.RequestResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

//https://github.com/eugenp/tutorials/blob/master/spring-boot-modules/spring-boot-basic-customization/src/main/java/com/baeldung/bootcustomfilters/FilterConfig.java
@Configuration
public class FilterConfig {

    public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestResponseLoggingFilter());
        registrationBean.addUrlPatterns("/account/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
