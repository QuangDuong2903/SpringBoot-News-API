package com.laptrinhjavaweb.service;

import org.springframework.http.ResponseEntity;

import com.laptrinhjavaweb.dto.RoleDTO;

public interface IRoleService {
	ResponseEntity<RoleDTO> save(RoleDTO roleDTO);
	ResponseEntity<RoleDTO> findByID(long id);
}
