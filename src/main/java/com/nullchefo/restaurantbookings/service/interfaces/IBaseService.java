package com.nullchefo.restaurantbookings.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.nullchefo.restaurantbookings.entity.BaseEntity;

public interface IBaseService<U extends BaseEntity> {

	public List<U> findAll();

	public U findBy(Long id);


	public Optional<U> findByIdOptional(Long id);

	public U create(U entity);


	public U update(U entity);


	public boolean delete(long id);


}


