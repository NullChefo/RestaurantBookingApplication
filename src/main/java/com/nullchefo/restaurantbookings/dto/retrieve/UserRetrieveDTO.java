package com.nullchefo.restaurantbookings.dto.retrieve;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

