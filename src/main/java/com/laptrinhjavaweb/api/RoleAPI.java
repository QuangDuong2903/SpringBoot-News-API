package com.laptrinhjavaweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.RoleDTO;
import com.laptrinhjavaweb.service.IRoleService;

@RestController
public class RoleAPI {
	
	@Autowired
	private IRoleService roleService;
	
	@PostMapping(value = "/role")
	public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO model) {
		return roleService.save(model);
	}
	
	@GetMapping(value = "/role/{id}")
	public ResponseEntity<RoleDTO> getRoleByID(@PathVariable("id") long id) {
		return roleService.findByID(id);
	}
}
