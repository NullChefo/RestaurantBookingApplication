/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
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

			// admin/admin
			User adminUser = User.builder()
								 .email("admin@example.com")
								 .hashedPassword(passwordEncoder.encode("admin"))
								 .firstName("Admin")
								 .lastName("Adminov")
								 .username("admin")
								 .role(RoleEnum.ADMIN.name())
								 .enabled(true)
								 .dateOfBirth(LocalDate.of(1900, 1, 1))
								 .important(true)
								 .roles(Set.of(RoleEnum.ADMIN))
								 .phone("0898888888")
								 .pictureURL("https://i.natgeofe.com/n/548467d8-c5f1-4551-9f58-6817a8d2c45e/NationalGeographic_2572187_3x4.jpg")
								 .build();
			userRepository.save(adminUser);


			// user/user
			User user = User.builder()
								 .email("user@example.com")
								 .hashedPassword(passwordEncoder.encode("user"))
								 .firstName("User")
								 .lastName("Userov")
								 .username("user")
								 .role(RoleEnum.CUSTOMER.name())
								 .enabled(true)
								 .dateOfBirth(LocalDate.of(1900, 1, 1))
								 .important(false)
								 .roles(Set.of(RoleEnum.CUSTOMER))
								 .phone("0898888858")
					.pictureURL("https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*")
								 .build();
			userRepository.save(user);

			//restaurant/restaurant
			User restaurantUser = User.builder()
									  .email("restaurant@example.com")
									  .hashedPassword(passwordEncoder.encode("restaurant"))
									  .firstName("Restaurant")
									  .lastName("Restaurantov")
									  .username("restaurant")
									  .role(RoleEnum.RESTAURANT.name())
									  .enabled(true)
									  .dateOfBirth(LocalDate.of(1900, 1, 1))
									  .important(true)
									  .roles(Set.of(RoleEnum.RESTAURANT))
									  .phone("0898888889")
					                   .pictureURL("https://kfcvarna.bg/upload/iblock/87c/87cc8fae35a2aebc270d1f910d6e940a.png")
									  .build();

			userRepository.save(restaurantUser);

			//			driver/driver
			User driverUser = User.builder()
								  .email("driver@example.com")
								  .hashedPassword(passwordEncoder.encode("driver"))
								  .firstName("Driver")
								  .lastName("Driverov")
									.username("driver")
								  .role(RoleEnum.DRIVER.name())
								  .enabled(true)
								  .dateOfBirth(LocalDate.of(1900, 1, 1))
								  .important(true)
								  .roles(Set.of(RoleEnum.DRIVER))
								  .phone("driver")
					.pictureURL("https://play-lh.googleusercontent.com/kDzXydb6ZT4LUj0RiU-GyptnVgCzzk9snN1FVxj2YfqFb4PpRdQRBKzdz4jzUOxAS9-d")
								  .build();
			userRepository.save(driverUser);

			// support/support
			User supportUser = User.builder()
								   .email("support@example.com")
								   .hashedPassword(passwordEncoder.encode("support"))
								   .firstName("Support")
								   .lastName("Supportov")
								   .username("support")
								   .role(RoleEnum.CUSTOMER_SUPPORT.name())
								   .enabled(true)
								   .dateOfBirth(LocalDate.of(1900, 1, 1))
								   .important(true)
								   .roles(Set.of(RoleEnum.CUSTOMER_SUPPORT))
								   .phone("support")
								   .pictureURL("https://www.svgrepo.com/show/192522/customer-service-support.svg")
								   .build();
			userRepository.save(supportUser);

		}

	}

}
