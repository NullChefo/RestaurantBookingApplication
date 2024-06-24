package com.nullchefo.restaurantbookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>,
		JpaSpecificationExecutor<PasswordResetToken> {

	PasswordResetToken findByToken(String token);

}

