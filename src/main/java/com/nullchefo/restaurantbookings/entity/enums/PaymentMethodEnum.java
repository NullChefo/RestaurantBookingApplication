/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
package com.nullchefo.restaurantbookings.entity.enums;

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
