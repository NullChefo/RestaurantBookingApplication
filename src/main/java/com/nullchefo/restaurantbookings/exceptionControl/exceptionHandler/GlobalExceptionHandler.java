package com.nullchefo.restaurantbookings.exceptionControl.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityAlreadyExistsException;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityNotFoundException;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(exception.getMessage());
	}

	@ExceptionHandler({ EntityAlreadyExistsException.class })
	public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(exception.getMessage());
	}

	@ExceptionHandler({ EntityNotValidException.class })
	public ResponseEntity<Object> handleEntityNotValidException(EntityNotValidException exception) {
		return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(exception.getMessage());
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(exception.getMessage());
	}
}
