package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Promotion;
import com.nullchefo.restaurantbookings.repository.PromotionRepository;

@Service
public class PromotionService extends BaseService<Promotion>{

	private final PromotionRepository promotionRepository;

	public PromotionService(final PromotionRepository promotionRepository) {
		this.promotionRepository = promotionRepository;
	}

	@Override
	protected JpaRepository<Promotion, UUID> getRepo() {
		return this.promotionRepository;
	}
}
