package com.nullchefo.restaurantbookings.agents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.service.OntologyManagerService;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.UserService;

import jade.core.Agent;

public class HostessAgent extends Agent {


	@Autowired
	private OntologyManagerService ontologyManagerService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReservationService reservationService;

	@Override
	protected void setup() {

	}

	// knows the current time
	// knows the current table
	// knows the current customer
	// knows the current order
	// knows the current menu
	// knows the current waiter


}
