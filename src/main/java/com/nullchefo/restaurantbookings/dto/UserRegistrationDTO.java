package com.nullchefo.restaurantbookings.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO extends BaseDTO {

	private String name;
	private String password;
	private String email;
	private String phone;
	private String address;
}
