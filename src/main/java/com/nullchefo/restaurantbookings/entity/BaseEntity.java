package com.nullchefo.restaurantbookings.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass //!!! cannot be inherited if not present
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	private UUID id;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("UTC"));

	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

}
