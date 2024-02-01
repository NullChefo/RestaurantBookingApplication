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
package com.nullchefo.restaurantbookings.utils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

@Component
public class MapperUtility {

	/**
	 * Copies properties from source to target, excluding null properties.
	 *
	 * @param src      The source object.
	 * @param target   The target object.
	 * @param nonEmpty List of properties that won't be copied if they are empty.
	 */
	public void copyProps(Object src, Object target, String... nonEmpty) {
		if (src == null) {
			target = null;
			return;
		}
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src, nonEmpty));
	}

	/**
	 * Gets the names of null properties in the source object.
	 *
	 * @param source   The source object.
	 * @param nonEmpty List of properties that won't be considered if they are empty.
	 * @return Array of null property names.
	 */
	public String[] getNullPropertyNames(Object source, String... nonEmpty) {
		if (source == null) {
			return new String[0];
		}

		BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			Object srcValue;
			try {
				srcValue = src.getPropertyValue(propertyDescriptor.getName());
			} catch (Exception e) {
				continue;
			}
			if (srcValue == null || (nonEmpty != null && Arrays.asList(nonEmpty).contains(propertyDescriptor.getName())
					&&
					!propertyDescriptor.getName().isEmpty() &&
					srcValue instanceof String &&
					srcValue.toString().isEmpty())) {
				emptyNames.add(propertyDescriptor.getName());
			}
		}
		return emptyNames.toArray(new String[0]);
	}

	/**
	 * Copies non-null properties from source to target.
	 *
	 * @param src    The source object.
	 * @param target The target object.
	 */
	public void copyNonNullProps(Object src, Object target) {
		copyProps(src, target);
	}
}
