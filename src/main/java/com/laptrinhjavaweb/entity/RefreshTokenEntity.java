package com.laptrinhjavaweb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "refreshtoken")
public class RefreshTokenEntity extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@Column(nullable = false, unique = true)
	private String token;
	
	@Column(nullable = false, name = "expirydate")
	private Date expiryDate;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
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
