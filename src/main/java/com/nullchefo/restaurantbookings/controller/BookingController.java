package com.nullchefo.restaurantbookings.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.service.BookingService;

@RestController()
@RequestMapping(path = "/booking")
public class BookingController {

	private final BookingService bookingService;

	private final ModelMapper modelMapper;

	public BookingController(final BookingService bookingService, final ModelMapper modelMapper) {
		this.bookingService = bookingService;
		this.modelMapper = modelMapper;
	}
}
