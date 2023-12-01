package com.nullchefo.restaurantbookings.entity;
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
public class Promotion extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	// TODO fix
	private String discount;

	private LocalDateTime expirationDate;

	// condition - little bit meta

}
