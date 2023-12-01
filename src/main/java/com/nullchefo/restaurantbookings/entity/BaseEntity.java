package com.nullchefo.restaurantbookings.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Indexed;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass //!!! cannot be inherited if not present
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	private UUID id;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

}
