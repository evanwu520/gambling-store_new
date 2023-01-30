package com.ampletec.boot.security.jwt;

import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecretStrategy<T extends TokenDetails> extends EncryptionStrategy<T> {

	private String jwtSecret;
	
	public SecretStrategy(ClaimsEncoder<T> claimsEncoder, String jwtSecret, int expires) {
		super(expires);
		this.claimsEncoder = claimsEncoder;
		this.jwtSecret = jwtSecret;
	}

	@Override
	public String encrypt(TokenDetails details, int expires) {
		// TODO Auto-generated method stub
		Date expiration = new Date(System.currentTimeMillis()+ expires*1000);  //设置1小时后过期
		return details.builder()
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(new Date())
				.setExpiration(expiration)
				.compressWith(CompressionCodecs.DEFLATE)
				.signWith(SignatureAlgorithm.HS256, jwtSecret)
				.compact();
	}

	@Override
	public T decrypt(String token) {
		// TODO Auto-generated method stub
		JwtParser parser = Jwts.parser().setSigningKey(jwtSecret);
		Jws<Claims> jwtClaims = parser.parseClaimsJws(token);
		Claims claims = jwtClaims.getBody();
//		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return this.claimsEncoder.encode(claims);
	}
}
