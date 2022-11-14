package com.laptrinhjavaweb.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.entity.RefreshTokenEntity;
import com.laptrinhjavaweb.repository.RefreshTokenRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.IRefreshTokenService;

@Service
public class RefreshTokenService implements IRefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	private final long RFT_EXPIRATION = 60000L;

	@Override
	public RefreshTokenEntity createRefreshToken(long userID) {
		Date now = new Date();
		RefreshTokenEntity refreshToken = new RefreshTokenEntity();
		refreshToken.setUser(userRepository.findById(userID).orElse(null));
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiryDate(new Date(now.getTime() + RFT_EXPIRATION));
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	@Override
	public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
		if (token.getExpiryDate().compareTo(new Date()) < 0) 
		{
			Long id = token.getUser().getId();
			refreshTokenRepository.delete(token);
			return createRefreshToken(id);
		}
		return token;
	}

	@Override
	public boolean isExpiredToken(RefreshTokenEntity token) {
		if (token.getExpiryDate().compareTo(new Date()) < 0) 
			return true;
		return false;
	}
}
