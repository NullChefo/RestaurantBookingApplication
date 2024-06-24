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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nullchefo.restaurantbookings.entity.Location;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.nullchefo.restaurantbookings.entity.Review;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;
import com.nullchefo.restaurantbookings.repository.ReservationRepository;
import com.nullchefo.restaurantbookings.repository.RestaurantRepository;
import com.nullchefo.restaurantbookings.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class HydrateDatabase {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ReservationRepository reservationRepository;

	private final RestaurantRepository restaurantRepository;

	@Autowired
	public HydrateDatabase(
			UserRepository userRepository, PasswordEncoder passwordEncoder,
			final ReservationRepository reservationRepository, RestaurantRepository restaurantRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.reservationRepository = reservationRepository;
		this.restaurantRepository = restaurantRepository;
	}

	@PostConstruct
	private void populateDatabase() {
		initUsers();
		initRestaurants();
	}

	private void initUsers() {
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
								 .pictureURL(
										 "https://i.natgeofe.com/n/548467d8-c5f1-4551-9f58-6817a8d2c45e/NationalGeographic_2572187_3x4.jpg")
								 .build();

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
							.pictureURL(
									"https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*")
							.build();

			// user/user
			User user1 = User.builder()
							 .email("user1@example.com")
							 .hashedPassword(passwordEncoder.encode("user1"))
							 .firstName("Other")
							 .lastName("Userov")
							 .username("user1")
							 .role(RoleEnum.CUSTOMER.name())
							 .enabled(true)
							 .dateOfBirth(LocalDate.of(1900, 1, 1))
							 .important(false)
							 .roles(Set.of(RoleEnum.CUSTOMER))
							 .phone("0898888848")
							 .pictureURL(
									 "https://i.pinimg.com/736x/21/74/01/217401dd349c077cdd3e71f61fba3a59.jpg")
							 .build();

			//restaurant/restaurant
			User restaurantUser = User.builder()
									  .email("org@example.com")
									  .hashedPassword(passwordEncoder.encode("org"))
									  .firstName("Org")
									  .lastName("Organizationov")
									  .username("org")
									  .role(RoleEnum.ORGANISATION.name())
									  .enabled(true)
									  .dateOfBirth(LocalDate.of(1900, 1, 1))
									  .important(true)
									  .roles(Set.of(RoleEnum.ORGANISATION))
									  .phone("0898888889")
									  .pictureURL(
											  "https://kfcvarna.bg/upload/iblock/87c/87cc8fae35a2aebc270d1f910d6e940a.png")
									  .build();

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
								  .pictureURL(
										  "https://play-lh.googleusercontent.com/kDzXydb6ZT4LUj0RiU-GyptnVgCzzk9snN1FVxj2YfqFb4PpRdQRBKzdz4jzUOxAS9-d")
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

			userRepository.saveAll(List.of(adminUser, user, user1, restaurantUser, driverUser, supportUser));
		}
	}

	private void initRestaurants() {
		if (restaurantRepository.count() == 0L) {

			Restaurant restaurant = Restaurant.builder()
											  .owner(userRepository.findByUsername("admin").get())
											  .name("Test Restaurant")
											  .pictureURL(
													  "https://podtepeto.com/wp-content/uploads/2022/11/img_6176-scaled.jpg")
											  .build();
			restaurantRepository.save(restaurant);

			Location location = Location.builder()
										.address("Location")
										.latitude("27")
										.longitude("49")
										.restaurant(restaurant)
										.build();

			Review review = Review.builder()
								  .rating((byte) 5)
								  .date(LocalDateTime.now())
								  .user(userRepository.findByUsername("admin").get())
								  .restaurant(restaurant)
								  .build();

			RestaurantTable restaurantTable = RestaurantTable.builder()
															 .name("1L")
															 .capacity(5)
															 .floorPosition(1)
															 .xPosition(3.5F)
															 .yPosition(1.6F)
															 .restaurant(restaurant)
															 .build();

			//  TODO set cuisine

			// TODO set menu

			// TODO set menu items

			restaurant.setLocation(location);
			restaurant.setReviews(List.of(review));
			restaurant.setTables(List.of(restaurantTable));
			restaurantRepository.save(restaurant);

		}
	}

}
