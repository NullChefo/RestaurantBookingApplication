package com.nullchefo.restaurantbookings.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.service.OrderService;

@RestController()
@RequestMapping(path = "/order")
public class OrderController {

	private final OrderService orderService;

	private final ModelMapper modelMapper;

	public OrderController(final OrderService orderService, final ModelMapper modelMapper) {
		this.orderService = orderService;
		this.modelMapper = modelMapper;
	}
}
