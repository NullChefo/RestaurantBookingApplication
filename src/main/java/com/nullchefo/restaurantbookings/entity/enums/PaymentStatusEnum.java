package com.nullchefo.restaurantbookings.entity.enums;

import lombok.Getter;

@Getter
public enum PaymentStatusEnum {
	PENDING("Pending"),
	SUCCESS("Success"),
	FAILURE("Failure"),
	REFUNDED("Refunded");

	private final String status;

	PaymentStatusEnum(String status) {
		this.status = status;
	}

}
