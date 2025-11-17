package com.learning.backend.security.jwt;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {
	
	private final String SECRET="mykey";
	private final Key key=Keys.hmacShaKeyFor(SECRET.getBytes());
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+8640000))
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsername(String token)
	{
		return null;
	}
	
	public boolean isTokenValid(String token) {
		return false;
	}

}
