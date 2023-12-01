package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Payment;
import com.nullchefo.restaurantbookings.repository.PaymentRepository;

@Service
public class PaymentService extends BaseService<Payment>{

	private final PaymentRepository paymentRepository;

	public PaymentService(final PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@Override
	protected JpaRepository<Payment, UUID> getRepo() {
		return this.paymentRepository;
	}
}
