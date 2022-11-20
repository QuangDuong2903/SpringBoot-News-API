package com.laptrinhjavaweb.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	//private final String JWT_SECRET = "quangduong";

	//private final long JWT_EXPIRATION = 604800000L;
	
	//private final long JWT_EXPIRATION = 3600000L;
	
	@Value("${jwt.JWT_SECRET}")
	private String JWT_SECRET;
	
	@Value("${jwt.JWT_EXPIRATION}")
	private long JWT_EXPIRATION;

	public String generateToken(Authentication authentication) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}
	
	public String generateToken(String username) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}
	
	public String getUserNameFromJWT(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(JWT_SECRET)
							.parseClaimsJws(token)
							.getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT token");
		}
		catch (ExpiredJwtException e) {
			System.out.println("Expired JWT token");
		}
		catch (UnsupportedJwtException e) {
			System.out.println("Unsupported JWT token");
		}
		catch (IllegalArgumentException e) {
			System.out.println("JWT claims string is empty");
		}
		return false;
	}
}
