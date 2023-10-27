package com.nullchefo.restaurantbookings.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.nullchefo.restaurantbookings.model.enums.OrderStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity(name = "orders")// postgres not good if order
public class Order{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToMany
	@JoinTable(name = "order_menu_items",
			   joinColumns = @JoinColumn(name = "order_id"),
			   inverseJoinColumns = @JoinColumn(name = "menu_item_id")
	)
	private List<MenuItem> items;



	private LocalDateTime orderedAt;
	private BigDecimal total;

	private OrderStatusEnum orderStatus;


}
