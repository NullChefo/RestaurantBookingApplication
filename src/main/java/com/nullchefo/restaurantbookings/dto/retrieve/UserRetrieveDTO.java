package com.nullchefo.restaurantbookings.dto.retrieve;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRetrieveDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;

	//	private String avatarURL;
	//	private boolean isOauth;
	//	private boolean verified;
	//	private String pronouns;
}

