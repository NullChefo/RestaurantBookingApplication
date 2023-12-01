package com.nullchefo.restaurantbookings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO can be user with role DonationRecipient
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class DonationRecipient  extends BaseEntity {
	private String name;
	@ManyToOne
	private Donation donation;

}
