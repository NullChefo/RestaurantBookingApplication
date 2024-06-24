package com.nullchefo.restaurantbookings.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.SentEmail;

@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail, UUID>, JpaSpecificationExecutor<SentEmail> {
}
