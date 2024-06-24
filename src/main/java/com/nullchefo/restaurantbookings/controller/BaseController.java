/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
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
