package com.ampletec.boot.security.jwt;

import io.jsonwebtoken.Claims;

public interface ClaimsEncoder<T extends TokenDetails> {
	T encode(Claims claims);
}
