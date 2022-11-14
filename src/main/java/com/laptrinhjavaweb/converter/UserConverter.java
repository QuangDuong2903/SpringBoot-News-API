package com.laptrinhjavaweb.converter;

import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.entity.RoleEntity;
import com.laptrinhjavaweb.entity.UserEntity;

@Component
public class UserConverter {

	public UserEntity toEntity(UserDTO dto) {
		UserEntity entity = new UserEntity();
		entity.setUserName(dto.getUserName());
		entity.setFullName(dto.getFullName());
		entity.setStatus(dto.getStatus());
		entity.setPassword(dto.getPassword());
		return entity;
	}

	public UserEntity toEntity(UserDTO dto, UserEntity entity) {
		if (dto.getUserName() != null)
			entity.setUserName(dto.getUserName());
		if (dto.getFullName() != null)
			entity.setFullName(dto.getFullName());
		if (dto.getStatus() != null)
			entity.setStatus(dto.getStatus());
		if (dto.getPassword() != null)
			entity.setPassword(dto.getPassword());
		return entity;
	}

	public UserDTO toDTO(UserEntity entity) {
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setFullName(entity.getFullName());
		dto.setPassword(entity.getPassword());
		dto.setStatus(entity.getStatus());
		dto.setUserName(entity.getUserName());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		for (RoleEntity roleEntity : entity.getRoles()) {
			dto.getRoles().add(roleEntity.getCode());
		}
		return dto;
	}
}
