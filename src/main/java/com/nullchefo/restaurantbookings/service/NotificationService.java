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
package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Notification;
import com.nullchefo.restaurantbookings.repository.NotificationRepository;

@Service
public class NotificationService extends BaseService<Notification> {

	private final NotificationRepository notificationRepository;

	public NotificationService(final NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	protected JpaRepository<Notification, UUID> getRepo() {
		return this.notificationRepository;
	}
}
