package com.nullchefo.restaurantbookings.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nullchefo.restaurantbookings.entity.Dish;
import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.service.OntologyManagerService;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.UserService;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@Component
public class HostessAgent extends Agent {


	private OntologyManagerService ontologyManagerService = new OntologyManagerService();
	@Autowired
	private UserService userService;
	@Autowired
	private ReservationService reservationService;

	@Override
	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();

		dfd.setName(getAID());
		sd.setType("restaurant");
		sd.setName("The Place");

		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		addBehaviour(cyclicBehaviour);

	}

	private CyclicBehaviour cyclicBehaviour = new CyclicBehaviour() {

		@Override
		public void action() {

			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);

			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {

				String ingredient = msg.getContent();

				ACLMessage reply = msg.createReply();

				// TODO it is null fix it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				System.out.println("Dish searching for " + ingredient);

				final ArrayList<Dish> dishesThatDoesNotContainIngredients = ontologyManagerService.getDishesThatDoesNotContainIngredients(
						ingredient);

				int size = dishesThatDoesNotContainIngredients.size();

				Dish[] result = dishesThatDoesNotContainIngredients.toArray(new Dish[size]);

				System.out.println(size + " dishes found");
				if (size > 0) {
					System.out.println("We have such dish");

					reply.setPerformative(ACLMessage.PROPOSE);

					ObjectMapper mapper = new ObjectMapper();

					try {
						reply.setContent(mapper.writeValueAsString(result));

						reply.setLanguage("JSON");

					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					reply.setPerformative(ACLMessage.INFORM_REF);
					reply.setContent("No food for you");
				System.out.println("No food found");
				}

				myAgent.send(reply);
			}
		}


	};
}
