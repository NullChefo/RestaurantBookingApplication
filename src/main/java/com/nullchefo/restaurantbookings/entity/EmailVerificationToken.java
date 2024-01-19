package com.nullchefo.restaurantbookings.entity;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "email-token")
@Entity
public class EmailVerificationToken extends BaseEntity {

	//Expiration time 60 minutes * 24 h
	private static final int EXPIRATION_TIME = 60 * 24;

	private String token;

	private Date expirationTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "users_id",
				nullable = false
			, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN")
	)
	private User user;

	public EmailVerificationToken(User user, String token) {
		super();
		this.token = token;
		this.user = user;
		this.expirationTime = calculateExpirationDate();
	}

	private Date calculateExpirationDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, EmailVerificationToken.EXPIRATION_TIME);
		return new Date(calendar.getTime().getTime());
	}
}
