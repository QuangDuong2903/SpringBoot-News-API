package com.laptrinhjavaweb.service;

import org.springframework.http.ResponseEntity;

import com.laptrinhjavaweb.dto.GoogleUserDTO;
import com.laptrinhjavaweb.dto.UserDTO;

public interface IUserService {
	ResponseEntity<UserDTO> save(UserDTO userDTO);
	ResponseEntity<UserDTO> findByID(long id);
	ResponseEntity<String> deleteByID(long id);
	UserDTO save(GoogleUserDTO googleUserDTO);
}
