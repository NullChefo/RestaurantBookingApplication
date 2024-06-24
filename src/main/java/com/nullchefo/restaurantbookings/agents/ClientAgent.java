package com.nullchefo.restaurantbookings.agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nullchefo.restaurantbookings.entity.Dish;
import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.service.OntologyManagerService;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.UserService;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@Component
public class ClientAgent extends Agent {

	@Autowired
	private OntologyManagerService ontologyManagerService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReservationService reservationService;

	private List<AID> restaurants;
	private String unwantedIngredients;

	@Override
	protected void setup() {

		// i need the restaurantId

		final Object[] arguments = this.getArguments();


		if (arguments != null) {
			this.unwantedIngredients = (String) arguments[0];
			addBehaviour(oneShotBehaviour);

		}

	}

	private OneShotBehaviour oneShotBehaviour = new OneShotBehaviour() {

		@Override
		public void action() {

			DFAgentDescription agentDesc = new DFAgentDescription();
			ServiceDescription servD = new ServiceDescription();

			servD.setType("restaurant");

			agentDesc.addServices(servD);

			try {
				DFAgentDescription[] descriptions = DFService.search(myAgent, agentDesc);
				restaurants = new ArrayList<AID>();

				for(int i = 0; i < descriptions.length; i++) {
					restaurants.add(descriptions[i].getName());
				}

			} catch (FIPAException e) {
				System.out.println("We got the restaurant: " + e.getMessage());
			}

			if(restaurants.size() > 0) {
				addBehaviour(new ReservationSerchBehaviour());
			}else {
				System.out.println("No Reservation");
			}
		}
	};


	private class ReservationSerchBehaviour extends Behaviour {

		int step = 0;
		MessageTemplate mt;
		HashMap<AID, Dish[]> dishes = new HashMap<AID, Dish[]>();
		int replyCounter = 0;

		@Override
		public void action() {

			switch(step) {
			case 0:

				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);

				for(int i = 0; i < restaurants.size(); i++) {
					cfp.addReceiver(restaurants.get(i));
				}

				cfp.setContent(unwantedIngredients);
				cfp.setConversationId("restaurant things");
				cfp.setReplyWith("cfp" + System.currentTimeMillis());

				mt = MessageTemplate.and(
						MessageTemplate.MatchConversationId("restaurant things"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));

				send(cfp);
				step++;

				break;
			case 1:

				ACLMessage reply = receive(mt);

				if(reply != null) {

					if(reply.getPerformative() == ACLMessage.PROPOSE) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							dishes.put(reply.getSender(),
									   mapper.readValue(reply.getContent(), Dish[].class));

							for(int i = 0; i < dishes.get(reply.getSender()).length; i++) {
								System.out.println(dishes.get(reply.getSender())[i]);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}

					}else {
						System.out.println(reply.getSender() + ": There is no Dish like this");
					}

					replyCounter++;

					if(replyCounter == restaurants.size()) {
						step++;
					}
				}

				break;

			case 2:
				//Chose Dish
				step++;
				break;

			}

		}

		@Override
		public boolean done() {
			if(step == 3) {
				doDelete();
				return true;
			}else if(dishes.keySet().size() == 0 && replyCounter == restaurants.size()) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				addBehaviour(oneShotBehaviour);
				return true;
			}

			return false;
		}

	}
}
