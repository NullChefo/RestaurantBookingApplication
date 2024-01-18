package com.nullchefo.restaurantbookings.entity;

import java.time.LocalDateTime;

import com.nullchefo.restaurantbookings.entity.enums.NotificationTypeEnum;

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
public class Notification extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String content;
	private NotificationTypeEnum notificationType;

	private LocalDateTime date;
}
