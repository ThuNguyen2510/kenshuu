package com.ks.utils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTGenerate {

	private static final String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

	private static final Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
			SignatureAlgorithm.HS256.getJcaName());

	public static String createJwtSignedHMAC(String userId) {
		//ユーザIDをpayloadに保持して、payloadとheaderをBase64でエンコードして、
		//その結果と”secret”をHS256でエンコードして、JWTを作成

		byte[] apiKeySecretBytes = Base64.getDecoder().decode(secret);

		Instant now = Instant.now();

		String jwtToken = Jwts.builder()
				.claim("userId", userId)
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
				.signWith(SignatureAlgorithm.HS256, apiKeySecretBytes)
				.compact();

		return jwtToken;
	}

	public static Claims parseJwt(String jwtString) {
		//エンコードを解除して、データを取る
		Claims jwt = Jwts.parser()
				.setSigningKey(hmacKey)
				.parseClaimsJws(jwtString)
				.getBody();

		return jwt;
	}

	/**
	 * Check if the token was issued by the server and if it's not expired
	 */
	public static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private static Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = parseJwt(token);
		return claimsResolver.apply(claims);
	}

}
