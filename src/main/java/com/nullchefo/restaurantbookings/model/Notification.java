package com.nullchefo.restaurantbookings.model;
import java.time.LocalDateTime;
import java.util.UUID;

import com.nullchefo.restaurantbookings.model.enums.NotificationTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	private String content;
	private NotificationTypeEnum notificationType;

	private LocalDateTime date;
}
