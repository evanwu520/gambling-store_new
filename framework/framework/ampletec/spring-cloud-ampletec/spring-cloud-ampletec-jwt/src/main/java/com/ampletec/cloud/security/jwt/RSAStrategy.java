package com.ampletec.cloud.security.jwt;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;

import com.ampletec.commons.encryption.RSA;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class RSAStrategy<T extends TokenDetails> extends EncryptionStrategy<T> {
	
	public RSAStrategy(ClaimsEncoder<T> claimsEncoder, String privateKey, String publicKey, int expires) {
		super(expires);
		this.claimsEncoder = claimsEncoder;
		try {
			this.privateKey = RSA.getPrivateKey(privateKey);
			this.publicKey = RSA.getPublicKey(publicKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private PrivateKey privateKey;
	private PublicKey publicKey;

	@Override
	public String encrypt(TokenDetails details, int expires) {
		// TODO Auto-generated method stub
		Date expiration = new Date(System.currentTimeMillis() + expires*1000);
		return details.builder()
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(new Date())
				.setExpiration(expiration)
				.compressWith(CompressionCodecs.DEFLATE)
				.signWith(SignatureAlgorithm.RS256, this.privateKey)
				.compact();
	}

	@Override
	public T decrypt(String token) {
		// TODO Auto-generated method stub
		Claims claims =  Jwts.parser().setSigningKey(this.publicKey).parseClaimsJws(token).getBody();
		return this.claimsEncoder.encode(claims);
	}
}
