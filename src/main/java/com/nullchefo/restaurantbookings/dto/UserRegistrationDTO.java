package com.nullchefo.restaurantbookings.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO extends BaseDTO {

	private String firstName;
	private String lastName;
	private boolean isOrganization;
	private boolean subscribeForMarketing;
	private boolean subscribeForAnnouncements;
	private boolean subscribeForNotifications;
	private String password;
	private String email;
	private String username;

}
