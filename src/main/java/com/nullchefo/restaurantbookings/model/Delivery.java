package com.nullchefo.restaurantbookings.model;
import java.util.UUID;

import com.nullchefo.restaurantbookings.model.enums.DeliveryStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Delivery extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne
	private Driver driver;


	// TODO make it entity
	private String location;

	private String notes;

	private DeliveryStatusEnum deliveryStatus;

	// Other delivery attributes (address, driver information, status, etc.)
}
