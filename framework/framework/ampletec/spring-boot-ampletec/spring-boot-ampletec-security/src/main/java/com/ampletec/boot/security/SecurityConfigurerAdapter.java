package com.ampletec.boot.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.CorsFilter;

import com.ampletec.boot.security.filter.OptionsRequestFilter;

public abstract class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Override
    public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/health");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()
	        .and()
		    .csrf().disable()
//		    .formLogin().disable()
//		    .logout()
//		    .logoutUrl("/logout")   //默认就是"/logout"
//		    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).
//		    and()
//		    .cors().disable();
		    .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class).cors()
		    .and().sessionManagement().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	protected abstract AuthenticationProvider authenticationProvider();
	
	protected abstract AuthenticationConfigurer<?,HttpSecurity> authenticationConfigurer();
}
