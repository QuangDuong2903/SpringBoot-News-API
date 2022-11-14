package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.entity.RefreshTokenEntity;

public interface IRefreshTokenService {
	RefreshTokenEntity createRefreshToken(long userID);
	RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);
	boolean isExpiredToken(RefreshTokenEntity token);
}
