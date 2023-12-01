package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Notification;
import com.nullchefo.restaurantbookings.repository.NotificationRepository;

@Service
public class NotificationService extends BaseService<Notification>{

	private final NotificationRepository notificationRepository;

	public NotificationService(final NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	protected JpaRepository<Notification, UUID> getRepo() {
		return this.notificationRepository;
	}
}
