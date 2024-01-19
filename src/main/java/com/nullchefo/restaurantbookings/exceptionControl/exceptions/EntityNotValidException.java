package com.nullchefo.restaurantbookings.exceptionControl.exceptions;

public class EntityNotValidException extends RuntimeException {

	public EntityNotValidException(final String message) {
		super(message);
	}

	public EntityNotValidException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public EntityNotValidException(final Throwable cause) {
		super(cause);
	}

	public EntityNotValidException(
			final String message,
			final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityNotValidException() {
	}
}
