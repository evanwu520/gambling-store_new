package com.ampletec.cloud.security.jwt;

import io.jsonwebtoken.Claims;

public interface ClaimsEncoder<T extends TokenDetails> {
	T encode(Claims claims);
}
