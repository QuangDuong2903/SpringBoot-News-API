package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.converter.UserConverter;
import com.laptrinhjavaweb.dto.GoogleUserDTO;
import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.entity.RoleEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.RoleRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public ResponseEntity<UserDTO> save(UserDTO userDTO) {
		UserEntity userEntity = null;
		if (userDTO.getId() != null) {
			UserEntity oldUserEntity = userRepository.findById(userDTO.getId()).orElse(null);
			userEntity = userConverter.toEntity(userDTO, oldUserEntity);
		} else {
			if (userRepository.findOneByUserNameAndStatus(userDTO.getUserName(), SystemConstant.ACTIVE_STATUS) != null)
				return ResponseEntity.badRequest().body(null);
			userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userEntity = userConverter.toEntity(userDTO);
		}
		if (userDTO.getRoles() != null) {
			List<RoleEntity> roles = new ArrayList<RoleEntity>();
			for (String role : userDTO.getRoles())
				roles.add(roleRepository.findOneByCode(role));
			userEntity.setRoles(roles);
		}
		userEntity = userRepository.save(userEntity);
		return ResponseEntity.ok().body(userConverter.toDTO(userEntity));
	}

	@Override
	public ResponseEntity<UserDTO> findByID(long id) {
		UserEntity userEntity = userRepository.findById(id).orElse(null);
		return ResponseEntity.ok().body(userConverter.toDTO(userEntity));
	}

	@Override
	public ResponseEntity<String> deleteByID(long id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
		return ResponseEntity.ok().body("Delete successfully");
	}

	@Override
	public UserDTO save(GoogleUserDTO googleUserDTO) {
		UserEntity userEntity = userConverter.toEntity(googleUserDTO);
		List<RoleEntity> roles = new ArrayList<>();
		roles.add(roleRepository.findOneByCode("ROLE_USER"));
		userEntity.setRoles(roles);
		return userConverter.toDTO(userRepository.save(userEntity));
	}
}
