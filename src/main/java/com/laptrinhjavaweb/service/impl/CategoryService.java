package com.laptrinhjavaweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.CategoryConverter;
import com.laptrinhjavaweb.dto.CategoryDTO;
import com.laptrinhjavaweb.entity.CategoryEntity;
import com.laptrinhjavaweb.repository.CategoryRepository;
import com.laptrinhjavaweb.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryConverter categoryConverter;

	@Override
	public ResponseEntity<CategoryDTO> save(CategoryDTO categoryDTO) {
		CategoryEntity categoryEntity = null;
		if(categoryDTO.getId() != null) {
			
		}
		else 
			categoryEntity = categoryConverter.toEntity(categoryDTO);
		categoryEntity = categoryRepository.save(categoryEntity);
		return ResponseEntity.ok().body(categoryConverter.toDTO(categoryEntity));
	}

	@Override
	public ResponseEntity<CategoryDTO> findByID(long id) {
		CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
		return ResponseEntity.ok().body(categoryConverter.toDTO(categoryEntity));
	}

}
