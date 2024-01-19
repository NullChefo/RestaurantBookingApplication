package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.SentEmail;
import com.nullchefo.restaurantbookings.repository.SentEmailRepository;

@Service
public class SentEmailService extends BaseService<SentEmail> {
	private final SentEmailRepository sentEmailRepository;

	@Autowired
	public SentEmailService(SentEmailRepository sentEmailRepository) {
		this.sentEmailRepository = sentEmailRepository;
	}

	@Override
	protected JpaRepository<SentEmail, UUID> getRepo() {
		return this.sentEmailRepository;
	}
}
