package com.laptrinhjavaweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.RoleConverter;
import com.laptrinhjavaweb.dto.RoleDTO;
import com.laptrinhjavaweb.entity.RoleEntity;
import com.laptrinhjavaweb.repository.RoleRepository;
import com.laptrinhjavaweb.service.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleConverter roleConverter;

	@Override
	public ResponseEntity<RoleDTO> save(RoleDTO roleDTO) {
		RoleEntity roleEntity = null;
		if (roleDTO.getId() != null) {

		} else
			roleEntity = roleConverter.toEntity(roleDTO);
		roleEntity = roleRepository.save(roleEntity);
		return ResponseEntity.ok().body(roleConverter.toDTO(roleEntity));
	}

	@Override
	public ResponseEntity<RoleDTO> findByID(long id) {
		RoleEntity roleEntity = roleRepository.findById(id).orElse(null);
		return ResponseEntity.ok().body(roleConverter.toDTO(roleEntity));
	}
}
