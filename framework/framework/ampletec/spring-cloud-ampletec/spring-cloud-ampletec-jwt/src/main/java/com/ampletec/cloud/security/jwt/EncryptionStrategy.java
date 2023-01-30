package com.ampletec.cloud.security.jwt;

public abstract class EncryptionStrategy<T extends TokenDetails> {
	
	public EncryptionStrategy(int defaultExpires) {
		this.defaultExpires = defaultExpires;
	}
	
	protected int defaultExpires;
	protected ClaimsEncoder<T> claimsEncoder;

	public abstract String encrypt(T details, int expires);
	
	public String encrypt(T details) {
		return this.encrypt(details, defaultExpires);
	}
	
	public abstract T decrypt(String token) ;
}
