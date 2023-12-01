package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Menu;
import com.nullchefo.restaurantbookings.repository.MenuRepository;

@Service
public class MenuService extends BaseService<Menu>{
	private final MenuRepository menuRepository;

	public MenuService(final MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@Override
	protected JpaRepository<Menu, UUID> getRepo() {
		return this.menuRepository;
	}
}
