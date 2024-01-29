package com.nullchefo.restaurantbookings.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.ContactFormSubmission;

@Repository
public interface ContactFormSubmissionRepository extends JpaRepository<ContactFormSubmission, UUID>,
		JpaSpecificationExecutor<ContactFormSubmission> {
}
