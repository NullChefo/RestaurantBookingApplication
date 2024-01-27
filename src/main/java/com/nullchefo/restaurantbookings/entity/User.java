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

	@ManyToMany
	@JoinTable(
			name = "customer_favorite_restaurants",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "restaurant_id")
	)
	private List<Restaurant> favoriteRestaurants;

	@OneToMany
	private List<Location> listOfLocations;


	@Column(nullable = false, length = 256)
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
	@Column(nullable = false, length = 256, unique = true)
	private String email;
	@Column(name = "LAST_LOGGED_AT")
	private LocalDateTime lastLoggedAt;
	@Column(length = 30)
	private String phone;
	@ManyToOne
	private Location currentLocation;

	@Lob
	@Column(length = 1000000)
	private byte[] profilePicture;

	private LocalDate dateOfBirth;
	private String occupation;
	private boolean important;

	// TODO!!! remove;
	// fails OrdersDetails
	private String role;

	// Management boolean values
	private boolean enabled = false; // email verified
	private boolean credentialsNonExpired = true;
	private boolean accountNotLocked = true;
	private boolean accountNotExpired = true;

	private boolean deleted = false;
}
