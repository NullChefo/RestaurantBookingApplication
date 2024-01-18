package com.nullchefo.restaurantbookings.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO extends BaseDTO{


	private String name;
	private String password;
	private String email;
	private String phone;
	private String address;
}
