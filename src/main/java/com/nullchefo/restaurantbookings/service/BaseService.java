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
package com.nullchefo.restaurantbookings.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nullchefo.restaurantbookings.entity.BaseEntity;
import com.nullchefo.restaurantbookings.entity.enums.EntityStatus;

/**
 * Abstract his means that the class cannot be instantiated on its own. It's meant to be subclassed, and concrete (non-abstract) subclasses are expected to provide implementations for abstract methods.
 * Generic Type (U extends BaseEntity): The class is parameterized with a generic type U, which extends or is a subtype of BaseEntity. This allows the class to work with different types of entities while ensuring that those entities are related to or derived from BaseEntity.
 * Implements Interface (implements IBaseService<U>): This class implements the IBaseService interface with the generic type U. This means that the class should provide concrete implementations for all methods declared in the IBaseService interface.
 *
 * @param <U>
 */
public abstract class BaseService<U extends BaseEntity> {
	protected abstract JpaRepository<U, UUID> getRepo();

	public List<U> findAll() {
		return getRepo().findAll();
	}

	public U findById(UUID id) {
		Optional<U> entity = getRepo().findById(id);
		final U result;
		result = entity.orElse(null);

		return result;
	}

	public Optional<U> findByIdOptional(UUID id) {
		return getRepo().findById(id);
	}

	public U create(U entity) {

		if (entity.getEntityStatus() != null) {
			entity.setEntityStatus(entity.getEntityStatus());
		} else {
			entity.setEntityStatus(EntityStatus.ACTIVE);
		}

		// updates if existing
		if (entity.getId() == null) {
			entity.setCreatedAt(LocalDateTime.now());
			return getRepo().save(entity);
		} else {
			// create historical entity
			return this.update(entity);
		}
	}

	public U update(U entity) {
		UUID id = entity.getId();
		Optional<U> optionalEntity = getRepo().findById(id);
		if (optionalEntity.isPresent()) {
			entity.setUpdatedAt(LocalDateTime.now());
			return getRepo().save(entity);
		}
		return null;
	}

	public boolean delete(UUID id) {
		Optional<U> optionalCategory = getRepo().findById(id);
		if (optionalCategory.isPresent()) {
			getRepo().delete(optionalCategory.get());
			return true;
		}
		return false;
	}

	public Page<U> list(Pageable pageable) {
		return getRepo().findAll(pageable);
	}

	public long count() {
		return getRepo().count();
	}

}
