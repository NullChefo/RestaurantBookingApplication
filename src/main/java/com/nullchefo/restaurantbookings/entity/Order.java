package com.nullchefo.restaurantbookings.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.nullchefo.restaurantbookings.entity.enums.OrderStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "orders")// postgres not good if order
public class Order extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User user;

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
