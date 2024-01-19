package com.nullchefo.restaurantbookings.entity;

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
public class SentEmail extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String subject;
	private String templateName;
	private String body;
	private boolean sent;

}
