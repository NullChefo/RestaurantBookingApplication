package com.nullchefo.restaurantbookings.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
