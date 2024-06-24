package com.nullchefo.restaurantbookings.agents.cron;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.nullchefo.restaurantbookings.agents.ClientAgent;
import com.nullchefo.restaurantbookings.configuration.jade.JadeConfiguration;
import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.UserService;

@Configuration
@EnableScheduling
public class ReservationManagementCron {

	private static final Logger log = LoggerFactory.getLogger(ReservationManagementCron.class);

	private final ReservationService reservationService;
	private final UserService userService;
	private final JadeConfiguration jadeConfiguration;

	private final Map<Reservation, User> setForProccesingMap = new HashMap<>();

	@Autowired
	public ReservationManagementCron(

			final ReservationService reservationService,
			final UserService userService, final JadeConfiguration jadeConfiguration) {

		this.reservationService = reservationService;
		this.userService = userService;
		this.jadeConfiguration = jadeConfiguration;
	}

	// every 5 seconds
	@Scheduled(fixedDelay = 10000)
	private void processReservation() {
		LocalDateTime now = LocalDateTime.now();
		// get reservation for now and the next 60 minutes

		List<Reservation> reservations = reservationService.findAllReservationsFromDateToDate(
				now.minusMinutes(60),
				now.plusMinutes(60));

		if (reservations.isEmpty()) {
			log.debug("No reservations found");
			return;
		}

		Map<Reservation, User> reservationUserMap = new HashMap<>();

		for (Reservation reservation : reservations) {
			User user = reservation.getUser();

			if (user == null) {
				log.debug("No user found for reservation{}", reservation);
				continue;
			}
			reservationUserMap.put(reservation, user);

		}

		log.debug("ReservationUserMap: {}", reservationUserMap);

		// No duplication

		if (!setForProccesingMap.isEmpty()) {
			for (Map.Entry<Reservation, User> entry : setForProccesingMap.entrySet()) {
				Reservation processingReservation = entry.getKey();
				// Remove from reservationUserMap if it contains a reservation with the same ID
				reservationUserMap.entrySet().removeIf(e -> e.getKey().getId().equals(processingReservation.getId()));
			}
		}

		for (Map.Entry<Reservation, User> entry : reservationUserMap.entrySet()) {
			Reservation reservation = entry.getKey();
			User user = entry.getValue();

			StringBuilder clientNameBuilder = new StringBuilder();
			clientNameBuilder.append(user.getFirstName())
							 .append("_")
							 .append(user.getLastName())
							 .append("___with_Reservation-Id_")
							 .append(reservation.getId());
			String clientName = clientNameBuilder.toString();

			try {
				Object[] args = new Object[]{reservation.getNotes()};
				jadeConfiguration.addAgentToMainContainer(clientName, ClientAgent.class.getName(), args);
				log.debug("Agent {} created", clientName);
				log.debug("args {}", Arrays.toString(args));
			} catch (Exception e) {
				log.error("Could not instantiate agent", e);
			}

			setForProccesingMap.put(reservation, user);
		}

	}

}
