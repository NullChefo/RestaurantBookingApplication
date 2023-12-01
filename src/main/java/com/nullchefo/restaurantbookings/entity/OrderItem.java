package com.nullchefo.restaurantbookings.entity;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@Entity
public class OrderItem extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "menu_item_id")
	private MenuItem menuItem;

	private String note;


	private BigDecimal priceWithoutVAT;

	private byte VATPercentage;


	// Other order item attributes (quantity, total price, etc.)
}
