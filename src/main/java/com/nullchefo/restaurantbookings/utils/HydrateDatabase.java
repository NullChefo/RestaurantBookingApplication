package com.nullchefo.restaurantbookings.utils;

import java.time.LocalDate;
import java.util.Set;

import javax.management.relation.RoleStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;
import com.nullchefo.restaurantbookings.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class HydrateDatabase {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public HydrateDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
	private void populateDatabase() {

		if (userRepository.count() == 0L) {

			User adminUser = User.builder()
								 .email("admin@example.com")
								 .hashedPassword(passwordEncoder.encode("admin"))

								 .firstName("admin")
								 .lastName("admin")
								 .username("admin")
								 .role(RoleEnum.ADMIN.name())
								 .enabled(true)
								 .dateOfBirth(LocalDate.of(1900, 1, 1))
								 .important(true)
								 .roles(Set.of(RoleEnum.ADMIN))
								 .phone("0898888888")
								 .build();

			//restaurant/restaurant
			User restaurantUser = User.builder()
									  .email("restaurant@example.com")
									  .hashedPassword(passwordEncoder.encode("restaurant"))
									  .firstName("restaurant")
									  .lastName("restaurant")
									  .username("restaurant")
									  .role(RoleEnum.RESTAURANT.name())
									  .enabled(true)
									  .dateOfBirth(LocalDate.of(1900, 1, 1))
									  .important(true)
									  .roles(Set.of(RoleEnum.RESTAURANT))
									  .phone("0898888889")
									  .build();

			userRepository.save(restaurantUser);

			//			driver/driver
			User driverUser = User.builder()
								  .email("driver@example.com")
								  .hashedPassword(passwordEncoder.encode("driver"))
								  .firstName("driver")
								  .lastName("driver")
								  .username("driver")
								  .role(RoleEnum.DRIVER.name())
								  .enabled(true)
								  .dateOfBirth(LocalDate.of(1900, 1, 1))
								  .important(true)
								  .roles(Set.of(RoleEnum.DRIVER))
								  .phone("driver")
								  .build();
			userRepository.save(driverUser);

			// support/support
			User supportUser = User.builder()
								   .email("support@example.com")
								   .hashedPassword(passwordEncoder.encode("support"))
								   .firstName("support")
								   .lastName("support")
								   .username("support")
								   .role(RoleEnum.CUSTOMER_SUPPORT.name())
								   .enabled(true)
								   .dateOfBirth(LocalDate.of(1900, 1, 1))
								   .important(true)
								   .roles(Set.of(RoleEnum.CUSTOMER_SUPPORT))
								   .phone("support")
								   .build();
			userRepository.save(supportUser);

		}

	}

}
