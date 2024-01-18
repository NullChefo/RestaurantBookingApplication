package com.nullchefo.restaurantbookings.dto;

import java.util.UUID;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass //!!! cannot be inherited if not present
@Getter
@Setter
@NoArgsConstructor
public class BaseDTO {
	private UUID id;
	//	private LocalDateTime createdAt;
	//	private LocalDateTime updatedAt;
}
