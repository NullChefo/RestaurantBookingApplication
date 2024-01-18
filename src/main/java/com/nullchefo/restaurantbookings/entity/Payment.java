package com.nullchefo.restaurantbookings.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nullchefo.restaurantbookings.entity.enums.PaymentMethodEnum;
import com.nullchefo.restaurantbookings.entity.enums.PaymentStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Payment extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User user;

	@ManyToOne
	private Order order;

	private PaymentStatusEnum paymentStatus;
	private PaymentMethodEnum paymentMethod;

	private BigDecimal total;
	private LocalDateTime dateTime;

}
