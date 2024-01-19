package com.nullchefo.restaurantbookings.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class DonationRecipient extends BaseEntity {
	private String name;

	@OneToMany(mappedBy = "donationRecipient")
	private List<Donation> donations;

}
