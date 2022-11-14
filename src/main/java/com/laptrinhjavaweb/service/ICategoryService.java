package com.laptrinhjavaweb.service;

import org.springframework.http.ResponseEntity;

import com.laptrinhjavaweb.dto.CategoryDTO;

public interface ICategoryService {
	ResponseEntity<CategoryDTO> save(CategoryDTO categoryDTO);
	ResponseEntity<CategoryDTO> findByID(long id);
}