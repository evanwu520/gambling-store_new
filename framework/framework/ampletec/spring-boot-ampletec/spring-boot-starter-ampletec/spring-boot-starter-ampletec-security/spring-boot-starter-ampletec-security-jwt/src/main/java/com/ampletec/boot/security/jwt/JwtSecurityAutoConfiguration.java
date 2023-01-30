package com.ampletec.boot.security.jwt;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.ampletec.boot.security.jwt.filter.TokenAuthenticationFilter;
import com.ampletec.commons.lang.SplitCharacter;
import com.ampletec.boot.security.AuthenticationConfigurer;
import com.ampletec.boot.security.SecurityConfigurerAdapter;
import com.ampletec.boot.security.handler.AccessDeineHandler;
import com.ampletec.boot.security.handler.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtSecurityProperties.class)
public class JwtSecurityAutoConfiguration extends SecurityConfigurerAdapter {

	@Autowired
	private JwtSecurityProperties jwtProperties;

	@Autowired
	private ClaimsEncoder<TokenDetails> claimsEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
	    		new Header("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type," + 
	    													jwtProperties.getTokenHeader() + SplitCharacter.COMMA + jwtProperties.getExpireHeader()),
	    		new Header("Access-Control-Expose-Headers", jwtProperties.getTokenHeader() + SplitCharacter.COMMA + jwtProperties.getExpireHeader()))));
		
		http.authorizeRequests()
	 	.antMatchers(jwtProperties.getPermissives()).permitAll().and()
	 	.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint())
	    .accessDeniedHandler(new AccessDeineHandler()).and()
	    .apply(authenticationConfigurer());	

		super.configure(http);
	}
	
	@Bean
	protected AuthenticationProvider authenticationProvider() {
		return new AuthenticationProvider(jwtStrategy());
	}
	
	@Bean
	@ConditionalOnMissingBean
	protected EncryptionStrategy<TokenDetails> jwtStrategy() {
		return new SecretStrategy<>(claimsEncoder, jwtProperties.getSecret(), jwtProperties.getExpires());
	}

	@Override
	protected AuthenticationConfigurer<?, HttpSecurity> authenticationConfigurer() {
		return new AuthenticationConfigurer<>(new TokenAuthenticationFilter(jwtStrategy(), jwtProperties));
	}
}
