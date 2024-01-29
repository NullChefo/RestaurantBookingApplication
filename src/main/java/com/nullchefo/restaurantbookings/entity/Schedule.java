/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
package com.nullchefo.restaurantbookings.entity;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Schedule extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ElementCollection
	@CollectionTable(name = "days_open", joinColumns = @JoinColumn(name = "schedule_id"))
	@Column(name = "day")
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> daysOpen = new HashSet<>();

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "openingTime", column = @Column(name = "opening_time")),
			@AttributeOverride(name = "closingTime", column = @Column(name = "closing_time"))
	})
	private HoursOfOperation hoursOfOperation;


	public Schedule() {
		this.hoursOfOperation = new HoursOfOperation();
	}

	public void addDayOpen(DayOfWeek dayOfWeek) {
		this.daysOpen.add(dayOfWeek);
	}

	public void removeDayOpen(DayOfWeek dayOfWeek) {
		this.daysOpen.remove(dayOfWeek);
	}

	public Set<DayOfWeek> getDaysOpen() {
		return Collections.unmodifiableSet(daysOpen);
	}


}

