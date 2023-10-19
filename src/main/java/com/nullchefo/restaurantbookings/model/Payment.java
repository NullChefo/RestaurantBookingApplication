package com.nullchefo.restaurantbookings.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private PaymentStatusEnum paymentStatus;
	private PaymentMethodEnum paymentMethod;

	private BigDecimal total;
	private LocalDateTime dateTime;


}
