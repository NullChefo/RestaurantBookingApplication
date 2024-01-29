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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "USERS")
public class User extends BaseEntity {

	// @ManyToMany: This annotation is used to indicate a many-to-many relationship between two entities.
	// In this case, it suggests that there is a many-to-many relationship between the entity that contains this field and the Restaurant entity.
	//
	//@JoinTable: This annotation is used to specify the details of the join table that will be used to manage the many-to-many relationship.
	// In a many-to-many relationship, a join table is a table that contains foreign key references to both participating tables.
	//
	//name = "customer_favorite_restaurants": This specifies the name of the join table in the database.
	// In this case, it's named "customer_favorite_restaurants".
	//
	//joinColumns = @JoinColumn(name = "user_id"): This specifies the foreign key column in the join table that references the primary key of the entity that contains this field.
	// The foreign key column is named "user_id".
	//
	//inverseJoinColumns = @JoinColumn(name = "restaurant_id"): This specifies the foreign key column in the join table that
	// references the primary key of the Restaurant entity. The foreign key column is named "restaurant_id".

	@ManyToMany
	@JoinTable(
			name = "customer_favorite_restaurants",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "restaurant_id")
	)
	private List<Restaurant> favoriteRestaurants;

	@OneToMany
	private List<Location> savedAddresses;

	@Column(nullable = false, length = 256, unique = true)
	private String username;

	@Column(nullable = false, length = 50)
	private String firstName;
	@Column( length = 100)
	private String lastName;

	@JsonIgnore
	private String hashedPassword;
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<RoleEnum> roles;
	@Email
	@Column(nullable = false, length = 256, unique = false)
	private String email;
	@Column(name = "LAST_LOGGED_AT")
	private LocalDateTime lastLoggedAt;
	@Column(length = 30)
	private String phone;
	@ManyToOne
	private Location currentLocation;


	@Column(length = 999)
	private String pictureURL;

	private LocalDate dateOfBirth;
	private String occupation;

	@Builder.Default
	private boolean important = false;

	// TODO!!! remove;
	// fails OrdersDetails
	private String role;


	@OneToMany(mappedBy = "user")
	private List<UserIPAddress> ipAddresses;

	// Management boolean values
	@Builder.Default
	private boolean enabled = false; // email verified
	@Builder.Default
	private boolean credentialsNonExpired = true;
	@Builder.Default
	private boolean accountNotLocked = true;
	@Builder.Default
	private boolean accountNotExpired = true;
	@Builder.Default
	private boolean deleted = false;

}
