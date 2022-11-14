package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.NewConverter;
import com.laptrinhjavaweb.dto.NewDTO;
import com.laptrinhjavaweb.entity.CategoryEntity;
import com.laptrinhjavaweb.entity.NewEntity;
import com.laptrinhjavaweb.repository.CategoryRepository;
import com.laptrinhjavaweb.repository.NewRepository;
import com.laptrinhjavaweb.service.INewService;

@Service
public class NewService implements INewService {

	@Autowired
	private NewRepository newRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private NewConverter newConverter;

	// in spring data jpa repository save() method has 2 role
	// insert add instance to database
	// update instance
	// save() will find id to determine which it has to do
	// if found id then update else add

	@Override
	public ResponseEntity<NewDTO> save(NewDTO newDTO) {
		NewEntity newEntity = null;
		// update data
		if (newDTO.getId() != null) {
			NewEntity oldNewEntity = newRepository.findById(newDTO.getId()).orElse(null);
			newEntity = newConverter.toEntity(newDTO, oldNewEntity);
		}
		// insert data
		else
			newEntity = newConverter.toEntity(newDTO);
		if (newDTO.getCategoryCode() != null) {
			CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
			newEntity.setCategory(categoryEntity);
		}
		newEntity = newRepository.save(newEntity);
		return ResponseEntity.ok().body(newConverter.toDTO(newEntity));
	}

	@Override
	public ResponseEntity<String> delete(long[] ids) {
		for (long id : ids) {
			newRepository.deleteById(id);
		}
		return ResponseEntity.ok().body("Delete successfully");
	}

	@Override
	public List<NewDTO> findAll(Pageable pageable) {
		List<NewDTO> results = new ArrayList<>();
		List<NewEntity> entities = newRepository.findAll(pageable).getContent();
		for (NewEntity item : entities) {
			NewDTO newDTO = newConverter.toDTO(item);
			results.add(newDTO);
		}
		return results;
	}

	@Override
	public int totalItem() {
		return (int) newRepository.count();
	}

}