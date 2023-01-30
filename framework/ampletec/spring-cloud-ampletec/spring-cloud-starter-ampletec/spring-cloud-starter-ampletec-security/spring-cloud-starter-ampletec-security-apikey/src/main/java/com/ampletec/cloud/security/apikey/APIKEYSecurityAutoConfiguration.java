package com.ampletec.cloud.security.apikey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

import com.ampletec.cloud.security.apikey.filter.APIKEYAuthenticationFilter;
import com.ampletec.cloud.security.AuthenticationConfigurer;
import com.ampletec.cloud.security.SecurityConfigurerAdapter;
import com.ampletec.cloud.security.filter.OptionsRequestFilter;

@Configuration
@EnableWebSecurity
@ConditionalOnBean(value = { UserDetailsService.class })
@EnableConfigurationProperties(APIKEYSecurityProperties.class)
public class APIKEYSecurityAutoConfiguration extends SecurityConfigurerAdapter {

	@Autowired
	protected UserDetailsService userDetailsService;
	@Autowired
	protected APIKEYSecurityProperties apikeyProperties;
	
	@Bean
	protected AuthenticationProvider authenticationProvider() {
		return new AuthenticationProvider(userDetailsService, apikeyProperties);
	}

	@Override
	protected AuthenticationConfigurer<?, HttpSecurity> authenticationConfigurer() {
		// TODO Auto-generated method stub
		return new AuthenticationConfigurer<>(new APIKEYAuthenticationFilter(apikeyProperties));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http.authorizeRequests()
			.antMatchers("/health").permitAll()
			.antMatchers("/api/**").authenticated()
//	        .anyRequest().authenticated()
	        .and()
		    .csrf().disable()
		    .formLogin().disable()
		    .cors()
		    .and()
		    .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class).cors()
		    .and()
		    .apply(authenticationConfigurer())
		    .and()
		    .logout()
		    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
		    .and()
		    .sessionManagement().disable();
	}
}
