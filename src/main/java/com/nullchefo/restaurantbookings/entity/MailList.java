package com.nullchefo.restaurantbookings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "mail_list")
@Entity
public class MailList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	@Builder.Default
	private boolean signedForAnnouncements = false;
	@Builder.Default
	private boolean signedForMarketing = false;
	@Builder.Default
	private boolean signedForNotifications = false;

	// Add to metrics
	@Builder.Default
	private Integer sentMailsForUser = 0;

}
