package com.nullchefo.restaurantbookings.model;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class User extends BaseEntity{

	// TODO fix USER entity exist

	@ManyToMany
	@JoinTable(name = "customer_favorite_restaurants",
			   joinColumns = @JoinColumn(name = "customer_id"),
			   inverseJoinColumns = @JoinColumn(name = "restaurant_id")
	)
	private List<Restaurant> favoriteRestaurants;

	// Other customer attributes (name, contact information, etc.)


	// TODO make it List<Location>
	private String locations;


	@Column(nullable = false, length = 256)
	private String name;
	@Column(nullable = false, length = 60)
	private String password;
	@Column(nullable = false, length = 256, unique = true)
	private String email;
	@Column(name = "IS_ACTIVE")
	private boolean isActive = true;
	@Column(name = "LAST_LOGGED_AT")
	private LocalDateTime lastLoggedAt;
	@Column(length = 30)
	private String phone;
	@Column(length = 256)
	private String address;


}
