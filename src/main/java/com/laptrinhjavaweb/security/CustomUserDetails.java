package com.laptrinhjavaweb.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.laptrinhjavaweb.entity.RoleEntity;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private long id;
	private String userName;
	private String password;
	private int enabled;
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public CustomUserDetails(long id, String userName, String password, int enabled, List<RoleEntity> roles) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (RoleEntity role : roles)
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		this.authorities = authorities;
	}
}
