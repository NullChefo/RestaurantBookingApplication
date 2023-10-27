package com.nullchefo.restaurantbookings.model.enums;

import lombok.Getter;

@Getter
public enum DeliveryStatusEnum {
	PENDING("Pending"),
	IN_TRANSIT("In Transit"),
	DELIVERED("Delivered"),
	FAILED("Delivery Failed");

	private final String status;

	DeliveryStatusEnum(String status) {
		this.status = status;
	}

}
