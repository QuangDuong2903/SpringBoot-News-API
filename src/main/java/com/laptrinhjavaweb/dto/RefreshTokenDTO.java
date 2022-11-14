package com.laptrinhjavaweb.dto;

import java.util.Date;

import com.laptrinhjavaweb.entity.RefreshTokenEntity;

public class RefreshTokenDTO extends BaseDTO<RefreshTokenEntity> {

	private long user;
	
	private String token;
	
	private Date expiryDate;

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
