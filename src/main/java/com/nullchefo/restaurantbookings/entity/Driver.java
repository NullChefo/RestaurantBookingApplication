package com.nullchefo.restaurantbookings.entity;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
public class Driver extends BaseEntity {

	// TODO fix
	@ManyToMany
	@JoinColumn(name = "delivery_id")
	private List<Delivery> deliveries;

	private String name;

	private String contactInformation;

	private String vehicleDetails;

}
