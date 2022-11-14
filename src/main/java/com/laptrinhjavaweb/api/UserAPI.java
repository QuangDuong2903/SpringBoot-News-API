package com.laptrinhjavaweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.service.IUserService;

@RestController
public class UserAPI {
	
	@Autowired
	private IUserService userService;

	@PostMapping(value = "/signup")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO model) {
		return userService.save(model);
	}
	
	@PutMapping(value = "/user/{id}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO model, @PathVariable("id") long id) {
		model.setId(id);
		return userService.save(model);
	}
	
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) {
		return userService.findByID(id);
	}
	
	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<String> deleteUserByID(@PathVariable("id") long id) {
		return userService.deleteByID(id);
	}
	
}
