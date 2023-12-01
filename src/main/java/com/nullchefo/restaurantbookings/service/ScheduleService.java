package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Schedule;
import com.nullchefo.restaurantbookings.repository.ScheduleRepository;

@Service
public class ScheduleService extends BaseService<Schedule> {

	private final ScheduleRepository scheduleRepository;

	public ScheduleService(final ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	@Override
	protected JpaRepository<Schedule, UUID> getRepo() {
		return this.scheduleRepository;
	}
}
