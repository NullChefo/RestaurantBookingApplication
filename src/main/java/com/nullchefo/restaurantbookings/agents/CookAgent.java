package com.nullchefo.restaurantbookings.agents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nullchefo.restaurantbookings.service.OntologyManagerService;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.UserService;

import jade.core.Agent;

@Component
public class CookAgent extends Agent {


	@Autowired
	private OntologyManagerService ontologyManagerService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReservationService reservationService;


	@Override
	protected void setup() {

	}

	// gets the order
	// get the recepy of the food from the ontology
	// prepares the food  according to the receppy
	// give the food to the waiter

}
