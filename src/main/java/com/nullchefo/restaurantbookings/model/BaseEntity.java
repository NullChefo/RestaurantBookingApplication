package com.nullchefo.restaurantbookings.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

}
