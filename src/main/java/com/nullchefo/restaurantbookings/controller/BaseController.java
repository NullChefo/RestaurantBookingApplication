package com.nullchefo.restaurantbookings.controller;

import com.nullchefo.restaurantbookings.dto.BaseDTO;
import com.nullchefo.restaurantbookings.entity.BaseEntity;

public class BaseController<E extends BaseEntity, D extends BaseDTO> {

	//	private final ModelMapper modelMapper;
	//
	//	public BaseController(ModelMapper modelMapper) {
	//		this.modelMapper = modelMapper;
	//	}

	//	private D convertToDTO(final E entity, Class<D> dtoClass) {
	//		return modelMapper.map(entity, dtoClass);
	//	}
	//
	//	private E convertToEntity(final D dto, Class<E> entityClass) {
	//		E entity = modelMapper.map(dto, entityClass);
	//		return entity;
	//	}
}
