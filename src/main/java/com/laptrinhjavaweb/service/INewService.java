package com.laptrinhjavaweb.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.laptrinhjavaweb.dto.NewDTO;

public interface INewService {
	ResponseEntity<NewDTO> save(NewDTO newDTO);
	ResponseEntity<String> delete(long[] ids);
	List<NewDTO> findAll(Pageable pageable);
	int totalItem();
}
