package com.nullchefo.restaurantbookings.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.model.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
