package com.nullchefo.restaurantbookings.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.service.CuisineService;

@RestController()
@RequestMapping(path = "/cuisine")
public class CuisineController {

	private CuisineService cuisineService;

	private final ModelMapper modelMapper;

	public CuisineController(final CuisineService cuisineService, final ModelMapper modelMapper) {
		this.cuisineService = cuisineService;
		this.modelMapper = modelMapper;
	}
}
