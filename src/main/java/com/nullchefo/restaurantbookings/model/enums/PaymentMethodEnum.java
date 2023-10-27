package com.nullchefo.restaurantbookings.model.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodEnum {
	CREDIT_CARD("Credit Card"),
	DEBIT_CARD("Debit Card"),
	PAYPAL("PayPal"),
	APPLE_PAY("Apple Pay"),
	GOOGLE_PAY("Google Pay"),
	CASH("Cash"),
	BANK_TRANSFER("Bank Transfer");

	private final String method;

	PaymentMethodEnum(String method) {
		this.method = method;
	}

}
