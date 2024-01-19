package com.nullchefo.restaurantbookings.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "driver")
	private List<Delivery> deliveries;

	private String name;

	private String contactInformation;

	private String vehicleDetails;

}
