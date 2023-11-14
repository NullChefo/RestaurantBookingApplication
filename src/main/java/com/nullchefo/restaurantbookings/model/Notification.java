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
public class Notification extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User user;
	private String content;
	private NotificationTypeEnum notificationType;

	private LocalDateTime date;
}
