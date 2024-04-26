package com.retail.ecom.jwt;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JWTService {

	@Value("${myapp.jwt.secret}")
	private String secret;

	@Value("${myapp.jwt.access.expiration}") 
	private long accessTokenExpiration;

	@Value("${myapp.jwt.refresh.expiration}") 
	private long refreshTokenExpiration;


	public String generateAccessToken(String user, String userrole) {
		return generateToken( user, accessTokenExpiration , userrole);
	}

	public String generateRefreshToken(String username, String role) {
		return generateToken(username, refreshTokenExpiration , role);
	}

	private Key getSignatureKey() {
		byte[] decode = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(decode);
	}


	public String getUserName(String token) {
		return parseJwtGetClaims(token).getSubject();
	}

	private Claims parseJwtGetClaims(String token) {
		JwtParserBuilder parserBuilder = Jwts.parserBuilder();
		JwtParser jwtParser = parserBuilder.setSigningKey(getSignatureKey()).build();
		Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
		Claims body = claimsJws.getBody();
		return body;
	}
	
	private String generateToken(String username, long expirationDate, String role) {
	    return Jwts.builder()
	            .setClaims(Map.of("role", role))
	            .setSubject(username)
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + expirationDate))
	            .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
	            .compact();
	}
	
	public String getUserRole(String token) {
		return parseJwtGetClaims(token).get("role", String.class);
	}

}
