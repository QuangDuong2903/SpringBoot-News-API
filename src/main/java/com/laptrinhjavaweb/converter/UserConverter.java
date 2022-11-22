package com.laptrinhjavaweb.converter;

import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.dto.GoogleUserDTO;
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
		entity.setAvatar(dto.getAvatar());
		entity.setEmail(dto.getEmail());
		entity.setType(SystemConstant.INTERNAL_ACCOUNT);
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
		if (dto.getAvatar() != null)
			entity.setAvatar(dto.getAvatar());
		if (dto.getEmail() != null)
			entity.setEmail(dto.getEmail());
		return entity;
	}

	public UserEntity toEntity(GoogleUserDTO dto) {
		UserEntity entity = new UserEntity();
		entity.setEmail(dto.getEmail());
		entity.setAvatar(dto.getPicture());
		entity.setType(SystemConstant.GOOGLE_ACCOUNT);
		String email = dto.getEmail();
		entity.setUserName(email.substring(0, email.indexOf("@")));
		entity.setFullName(email.substring(0, email.indexOf("@")));
		entity.setStatus(SystemConstant.ACTIVE_STATUS);
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
		dto.setAvatar(entity.getAvatar());
		dto.setEmail(entity.getEmail());
		dto.setType(entity.getType());
		for (RoleEntity roleEntity : entity.getRoles()) {
			dto.getRoles().add(roleEntity.getCode());
		}
		return dto;
	}
}
