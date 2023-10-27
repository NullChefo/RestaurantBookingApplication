package com.nullchefo.restaurantbookings.model.enums;

import lombok.Getter;

@Getter
public enum NotificationTypeEnum {
	EMAIL("Email"),
	SMS("SMS"),
	PUSH("Push Notification"),
	IN_APP("In-App Notification");

	private final String type;

	NotificationTypeEnum(String type) {
		this.type = type;
	}

}
