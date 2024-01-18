package com.nullchefo.restaurantbookings.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

	// Other customer attributes (name, contact information, etc.)

	// TODO make it List<Location>

	@OneToMany
	private List<Location> listOfLocations;

	@Column(nullable = false, length = 256)
	private String name;
	private boolean deleted = false;
	@Column(name = "password", length = 1000, columnDefinition = "text", nullable = false)
	private String password;
	@Column(nullable = false, length = 256, unique = true)
	private String email;
	@Column(name = "IS_ACTIVE")
	private boolean isActive = true;
	@Column(name = "IS_EXPIRED")
	private boolean isExpired = false;
	@Column(name = "LAST_LOGGED_AT")
	private LocalDateTime lastLoggedAt;
	@Column(length = 30)
	private String phone;
	@ManyToOne
	private Location currentLocation;



	// Management boolean values

	@Builder.Default
	private boolean enabled = false; // email verified
	@Builder.Default
	private boolean credentialsNonExpired = true;
	@Builder.Default
	private boolean accountNotLocked = true;
	@Builder.Default
	private boolean accountNotExpired = true;

}
