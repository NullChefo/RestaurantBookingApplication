package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Delivery;
import com.nullchefo.restaurantbookings.repository.DeliveryRepository;

@Service
public class DeliveryService extends BaseService<Delivery>{

	private final DeliveryRepository deliveryRepository;

	public DeliveryService(final DeliveryRepository deliveryRepository) {
		this.deliveryRepository = deliveryRepository;
	}

	@Override
	protected JpaRepository<Delivery, UUID> getRepo() {
		return this.deliveryRepository;
	}
}
