package com.nullchefo.restaurantbookings.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.service.UserService;

@RestController()
@RequestMapping(path = "/user")
public class UserController{

	private final UserService userService;

	private final ModelMapper modelMapper;
	public UserController(final UserService userService, final ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
}
