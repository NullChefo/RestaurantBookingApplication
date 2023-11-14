package com.nullchefo.restaurantbookings.model;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Review extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User user;

	// -127 to 127
	private byte rating;

	private LocalDateTime date;

}
