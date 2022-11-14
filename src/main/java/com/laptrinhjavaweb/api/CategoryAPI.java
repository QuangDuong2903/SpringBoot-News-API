package com.laptrinhjavaweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.CategoryDTO;
import com.laptrinhjavaweb.service.ICategoryService;

@RestController
public class CategoryAPI {
	
	@Autowired
	private ICategoryService categoryService;

	@PostMapping(value = "/category")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO model) {
		return categoryService.save(model);
	}
	
	@GetMapping(value = "/category/{id}")
	public ResponseEntity<CategoryDTO> getCategoryByID(@PathVariable("id") long id) {
		return categoryService.findByID(id);
	}
}
