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
package com.nullchefo.restaurantbookings.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Restaurant extends BaseEntity {
	private String name;

	@ManyToOne
	private User owner;

	@Column(length = 999)
	private String pictureURL;

	@OneToOne
	private Location location;

	@OneToMany(mappedBy = "restaurant")
	private List<Menu> menus;

	@OneToMany(mappedBy = "restaurant")
	private List<Schedule> schedules;

	//	@OneToMany(mappedBy = "restaurant")
	//	private List<Booking> bookings;

	@OneToMany(mappedBy = "restaurant")
	private List<Donation> donations;

	@OneToMany(mappedBy = "restaurant")
	private List<Cuisine> cuisines;

	@OneToMany(mappedBy = "restaurant")
	private List<Review> reviews;

	@OneToMany(mappedBy = "restaurant")
	private List<RestaurantTable> tables;

}
