package com.ampletec.cloud.security.jwt;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.ampletec.cloud.security.AuthenticationConfigurer;
import com.ampletec.cloud.security.SecurityConfigurerAdapter;
import com.ampletec.cloud.security.jwt.filter.TokenAuthenticationFilter;

@Configuration
@EnableConfigurationProperties(JwtSecurityProperties.class)
public class JwtSecurityAutoConfiguration extends SecurityConfigurerAdapter {

	@Autowired
	private JwtSecurityProperties jwtProperties;

	@Autowired
	private ClaimsEncoder<TokenDetails> claimsEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
	 	.antMatchers(jwtProperties.getPermissives()).permitAll()
	 	.and()
	    .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
	    		new Header("Access-Control-Allow-Headers", "XY,DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization"),
	    		new Header("Access-Control-Expose-Headers", jwtProperties.getHeader()))));
		

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
