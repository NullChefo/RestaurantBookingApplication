package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Order;
import com.nullchefo.restaurantbookings.repository.OrderRepository;

@Service
public class OrderService extends BaseService<Order> {
	private final OrderRepository orderRepository;

	public OrderService(final OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	protected JpaRepository<Order, UUID> getRepo() {
		return this.orderRepository;
	}
}
