package com.laptrinhjavaweb.dto;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO extends BaseDTO<RoleDTO> {

	private String name;
	private String code;
	private List<Long> users = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Long> getUsers() {
		return users;
	}
	public void setUsers(List<Long> users) {
		this.users = users;
	}
}
