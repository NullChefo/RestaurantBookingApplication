package com.nullchefo.restaurantbookings.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.service.PaymentService;

@RestController()
@RequestMapping(path = "/payment")
public class PaymentController {

	private final PaymentService paymentService;

	private final ModelMapper modelMapper;

	public PaymentController(final PaymentService paymentService, final ModelMapper modelMapper) {
		this.paymentService = paymentService;
		this.modelMapper = modelMapper;
	}
}
