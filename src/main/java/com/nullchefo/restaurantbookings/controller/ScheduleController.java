package com.nullchefo.restaurantbookings.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.restaurantbookings.service.ScheduleService;
@RestController()
@RequestMapping(path = "/schedule")
public class ScheduleController {
	private final ScheduleService scheduleService;
	private final ModelMapper modelMapper;

	public ScheduleController(final ScheduleService scheduleService, final ModelMapper modelMapper) {
		this.scheduleService = scheduleService;
		this.modelMapper = modelMapper;
	}
}
