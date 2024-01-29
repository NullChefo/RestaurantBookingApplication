/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
package com.nullchefo.restaurantbookings.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.dto.UserPasswordChangeDTO;
import com.nullchefo.restaurantbookings.dto.UserRegistrationDTO;
import com.nullchefo.restaurantbookings.entity.EmailVerificationToken;
import com.nullchefo.restaurantbookings.entity.MailList;
import com.nullchefo.restaurantbookings.entity.PasswordResetToken;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.UserIPAddress;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityAlreadyExistsException;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityNotFoundException;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityNotValidException;
import com.nullchefo.restaurantbookings.repository.EmailVerificationTokenRepository;
import com.nullchefo.restaurantbookings.repository.MailListRepository;
import com.nullchefo.restaurantbookings.repository.PasswordResetTokenRepository;
import com.nullchefo.restaurantbookings.repository.UserRepository;
import com.nullchefo.restaurantbookings.utils.MapperUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService extends BaseService<User> {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final EmailVerificationTokenRepository emailVerificationTokenRepository;

	private final PasswordResetTokenRepository passwordResetTokenRepository;

	private final MailProduceService mailProduceService;

	private final MapperUtility mapperUtility;

	private final MailListRepository mailListRepository;

	private final boolean isDevelopmentEnvironment;
	private final UserIPAddressService userIPAddressService;

	@Autowired
	public UserService(
			final UserRepository userRepository,
			final PasswordEncoder passwordEncoder,
			final EmailVerificationTokenRepository emailVerificationTokenRepository,
			PasswordResetTokenRepository passwordResetTokenRepository,
			MailProduceService mailProduceService, MapperUtility mapperUtility, MailListRepository mailListRepository,
			@Value("${properties.is_development_environment}") boolean isDevelopmentEnvironment,
			final UserIPAddressService userIPAddressService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailVerificationTokenRepository = emailVerificationTokenRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.mailProduceService = mailProduceService;
		this.mapperUtility = mapperUtility;
		this.mailListRepository = mailListRepository;
		this.isDevelopmentEnvironment = isDevelopmentEnvironment;
		this.userIPAddressService = userIPAddressService;
	}

	@Override
	protected JpaRepository<User, UUID> getRepo() {
		return this.userRepository;
	}

	public void registerUser(final UserRegistrationDTO userDto) {

		final Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

		if (optionalUser.isPresent()) {
			log.error(String.format("User with username %s already exists!", userDto.getUsername()));
			throw new EntityAlreadyExistsException("User with that username already exists!");
		}

		User user = new User();
		mapperUtility.copyProps(userDto, user);
		user.setHashedPassword(passwordEncoder.encode(userDto.getPassword()));

		if (userDto.isOrganization()) {
			user.setRole("COMPANY");
		} else {
			user.setRole("USER");
		}

		final String validationToken = UUID.randomUUID().toString();

		if (isDevelopmentEnvironment) {
			user.setEnabled(true);
		} else {
			user.setEnabled(false);
		}

		try {
			userRepository.save(user);
		} catch (Exception e) {

			log.error(String.format("Error in saving {%s} with error: {%s}", user, e));
			throw e;
		}

		userRepository.save(user);

		Thread.ofVirtual().start(() -> {
			// sets the verification token for the user
			saveUserIntoMailList(user, userDto);
		});

		Thread.ofVirtual().start(() -> {
			// sets the verification token for the user
			saveVerificationTokenForUser(validationToken, user);
		});

		Thread.ofVirtual().start(() -> {
			// // sends the verification token
			mailProduceService.sendEmailVerification(user, validationToken);
		});

	}

	private void saveUserIntoMailList(final User user, final UserRegistrationDTO userRegisterDTO) {
		MailList mailList = MailList
				.builder()
				.signedForNotifications(userRegisterDTO.isSubscribeForNotifications())
				.signedForAnnouncements(userRegisterDTO.isSubscribeForAnnouncements())
				.signedForMarketing(userRegisterDTO.isSubscribeForMarketing())
				.user(user)
				.build();

		log.trace(String.valueOf(mailList));
		mailListRepository.save(mailList);
	}

	private void saveVerificationTokenForUser(String token, User user) {
		EmailVerificationToken verificationToken
				= new EmailVerificationToken(user, token);
		log.trace(String.valueOf(verificationToken));

		emailVerificationTokenRepository.save(verificationToken);
	}

	public void changePassword(final UserPasswordChangeDTO passwordChangeDTO) {
		// check if deleted is required
		Optional<User> user = userRepository.findByEmailIgnoreCase(passwordChangeDTO.getEmail());

		if (user.isEmpty()) {
			log.error(String.format("User with email %s already exists!", passwordChangeDTO.getEmail()));
			throw new EntityNotFoundException("User not found");
		}

		if (!checkIfValidOldPassword(user.get(), passwordChangeDTO.getOldPassword())) {
			throw new EntityNotValidException("Invalid old password");

		}
	}

	public boolean validateVerificationToken(String token) {
		EmailVerificationToken verificationToken
				= emailVerificationTokenRepository.findByToken(token);

		if (verificationToken == null) {
			// throw new EntityNotValidException("Token is not valid");
			log.trace("Token is not null");
		}

		User user = verificationToken.getUser();

		Calendar cal = Calendar.getInstance();

		if ((verificationToken.getExpirationTime().getTime()
				- cal.getTime().getTime()) <= 0) {
			emailVerificationTokenRepository.delete(verificationToken);
			// throw new EntityNotValidException("Token is expired");
			log.trace(String.format("Token is expired %s", token));
			return false;
		}
		user.setEnabled(true);
		userRepository.save(user);
		emailVerificationTokenRepository.delete(verificationToken);
		log.trace(String.valueOf(verificationToken));
		return true;
	}

	private EmailVerificationToken generateNewVerificationToken(String oldToken) {
		EmailVerificationToken verificationToken
				= emailVerificationTokenRepository.findByToken(oldToken);
		if (verificationToken == null) {
			return null;
		}
		emailVerificationTokenRepository.delete(verificationToken);

		verificationToken.setToken(UUID.randomUUID().toString());
		emailVerificationTokenRepository.save(verificationToken);
		log.trace(String.valueOf(verificationToken));

		return verificationToken;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmailIgnoreCaseAndDeleted(email, false).orElse(null);
	}

	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken passwordResetToken
				= new PasswordResetToken(user, token);
		passwordResetTokenRepository.save(passwordResetToken);
	}

	public void validatePasswordResetToken(String token) {
		PasswordResetToken passwordResetToken
				= passwordResetTokenRepository.findByToken(token);

		if (passwordResetToken == null) {
			throw new EntityNotValidException("Invalid");
		}

		User user = passwordResetToken.getUser();
		Calendar cal = Calendar.getInstance();

		if ((passwordResetToken.getExpirationTime().getTime()
				- cal.getTime().getTime()) <= 0) {
			passwordResetTokenRepository.delete(passwordResetToken);
			throw new EntityNotValidException("Expired");
		}

	}

	public Optional<User> getUserByPasswordResetToken(String token) {
		return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
	}

	public void changePassword(User user, String newPassword) {
		user.setHashedPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	public boolean checkIfValidOldPassword(User user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getHashedPassword());
	}

	public void savePassword(final String token, final UserPasswordChangeDTO passwordChangeDTO) {

		if (passwordChangeDTO.getNewPassword() == null) {
			throw new EntityNotValidException("New password can not be empty!");
		}
		validatePasswordResetToken(token);
		Optional<User> user = getUserByPasswordResetToken(token);
		if (user.isPresent()) {
			changePassword(user.get(), passwordChangeDTO.getNewPassword());

		} else {
			throw new EntityNotValidException("Invalid token!");
		}
	}

	public boolean resendVerificationToken(final String token) {
		EmailVerificationToken emailVerificationToken
				= generateNewVerificationToken(token);
		if (emailVerificationToken == null) {
			//	throw new EntityNotValidException("Invalid token!");
			log.trace("Invalid token!");
		}

		User user = emailVerificationToken.getUser();
		mailProduceService.sendEmailVerification(user, emailVerificationToken.getToken());
		return true;
	}

	public void passwordResetTokenMail(final User user, String token) {
		mailProduceService.passwordResetTokenMail(user, token);
	}

	public void resetPassword(final UserPasswordChangeDTO passwordModel) {
		User user = findUserByEmail(passwordModel.getEmail());
		if (user != null) {
			String token = UUID.randomUUID().toString();
			createPasswordResetTokenForUser(user, token);
			passwordResetTokenMail(user, token);
		} else {
			throw new EntityNotFoundException("User with this email does not exist!");
		}

	}

	public Optional<User> findByUsername(final String username) {
		return this.userRepository.findByUsername(username);
	}

	// TODO implement
	public void sendMailIfLoggedInFromAnotherIpAddress(final User user) {

		List<UserIPAddress> ipAddressList = this.userIPAddressService.findAllForUser(user);

		throw new NotImplementedException("Not Implemented");
	}
}
