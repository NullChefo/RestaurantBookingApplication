package com.nullchefo.restaurantbookings.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
	PENDING("Pending"),
	PROCESSING("Processing"),
	SHIPPED("Shipped"),
	DELIVERED("Delivered"),
	CANCELLED("Cancelled");

	private final String status;

	private OrderStatusEnum(String status) {
		this.status = status;
	}

}
