package com.nullchefo.restaurantbookings.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.service.DeliveryService;

@RestController()
@RequestMapping(path = "/delivery")
public class DeliveryController {


	private final DeliveryService deliveryService;
	private final ModelMapper modelMapper;

	public DeliveryController(final DeliveryService deliveryService, final ModelMapper modelMapper) {
		this.deliveryService = deliveryService;
		this.modelMapper = modelMapper;
	}
}
