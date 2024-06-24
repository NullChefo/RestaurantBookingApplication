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
package com.nullchefo.restaurantbookings.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.nullchefo.restaurantbookings.entity.BaseEntity;

public interface IBaseService<U extends BaseEntity> {

	List<U> findAll();

	U findBy(Long id);

	Optional<U> findByIdOptional(Long id);

	U create(U entity);

	U update(U entity);

	boolean delete(long id);

}


