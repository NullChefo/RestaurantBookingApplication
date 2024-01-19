package com.nullchefo.restaurantbookings.exceptionControl.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
	public EntityAlreadyExistsException(String message) {
		super(message);
	}
}
