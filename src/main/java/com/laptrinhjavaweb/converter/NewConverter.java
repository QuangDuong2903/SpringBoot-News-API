package com.laptrinhjavaweb.converter;

import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.NewDTO;
import com.laptrinhjavaweb.entity.NewEntity;

@Component
public class NewConverter {
	public NewEntity toEntity(NewDTO dto) {
		NewEntity entity = new NewEntity();
		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());
		entity.setShortDescription(dto.getShortDescription());
		entity.setThumbnail(dto.getThumbnail());
		return entity;
	}

	public NewDTO toDTO(NewEntity entity) {
		NewDTO dto = new NewDTO();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setContent(entity.getContent());
		dto.setShortDescription(entity.getShortDescription());
		dto.setThumbnail(entity.getThumbnail());
		dto.setCategoryCode(entity.getCategory().getCode());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		return dto;
	}

	public NewEntity toEntity(NewDTO dto, NewEntity entity) {
		if (dto.getTitle() != null)
			entity.setTitle(dto.getTitle());
		if (dto.getContent() != null)
			entity.setContent(dto.getContent());
		if (dto.getShortDescription() != null)
			entity.setShortDescription(dto.getShortDescription());
		if (dto.getThumbnail() != null)
			entity.setThumbnail(dto.getThumbnail());
		return entity;
	}
}
