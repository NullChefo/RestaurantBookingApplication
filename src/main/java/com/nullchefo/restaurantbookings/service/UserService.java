package com.nullchefo.restaurantbookings.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.webjars.NotFoundException;

import com.nullchefo.restaurantbookings.dto.UserPasswordChangeDTO;
import com.nullchefo.restaurantbookings.dto.UserRegistrationDTO;
import com.nullchefo.restaurantbookings.entity.EmailVerificationToken;
import com.nullchefo.restaurantbookings.entity.PasswordResetToken;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityAlreadyExistsException;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityNotFoundException;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityNotValidException;
import com.nullchefo.restaurantbookings.repository.EmailVerificationTokenRepository;
import com.nullchefo.restaurantbookings.repository.PasswordResetTokenRepository;
import com.nullchefo.restaurantbookings.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService extends BaseService<User> {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final EmailVerificationTokenRepository emailVerificationTokenRepository;

	private final PasswordResetTokenRepository passwordResetTokenRepository;

	private final MailProduceService mailProduceService;

	@Autowired
	public UserService(
			final UserRepository userRepository,
			final PasswordEncoder passwordEncoder,
			final EmailVerificationTokenRepository emailVerificationTokenRepository,
			PasswordResetTokenRepository passwordResetTokenRepository,
			MailProduceService mailProduceService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailVerificationTokenRepository = emailVerificationTokenRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.mailProduceService = mailProduceService;
	}

	@Override
	protected JpaRepository<User, UUID> getRepo() {
		return this.userRepository;
	}

	public void createUser(@RequestBody final UserRegistrationDTO userRegistrationDTO) {

		final Optional<User> optionalUser = userRepository.findByEmail(userRegistrationDTO.getEmail());

		if (optionalUser.isPresent()) {
			log.error(String.format("User with email %s already exists!", userRegistrationDTO.getEmail()));
			throw new EntityAlreadyExistsException("User with that email already exists!");
		}

		//		user = new User();
		//
		//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//
		//		final String validationToken = UUID.randomUUID().toString();
		//
		//
		//
		//		user.setRole("USER");
		//
		//		user.setVerified(false);
		//
		//		user.setVerifiedToken(mapperUtility.generateToken());
		//
		//		user.setVerifiedTokenExpiry(mapperUtility.generateTokenExpiry());
		//
		//		user.setVerificationSent(false);
		//
		//		user.setVerificationSentOn(mapperUtility.generateTimestamp());
		//
		//		user.setVerificationSentTo(user.getEmail());
		//
		//		user.setVerificationSentVia("EMAIL");
		//
		//
		//
		//
		//		userRepository.save(user);
		//
		//
		//
		//		Thread.ofVirtual().start(() -> {
		//			// sets the verification token for the user
		//			saveVerificationTokenForUser(token, user);
		//		});
		//
		//
		//
		//		Thread.ofVirtual().start(() -> {
		//			// // sends the verification token
		//			mailProducerService.sendEmailVerification(user, token);
		//		});

	}

	private void saveVerificationTokenForUser(String token, User user) {
		//		EmailVerificationToken verificationToken
		//				= new EmailVerificationToken(user, token);
		//
		//		emailVerificationTokenRepository.save(verificationToken);
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

	public void validateVerificationToken(String token) {
		EmailVerificationToken verificationToken
				= emailVerificationTokenRepository.findByToken(token);

		if (verificationToken == null) {
			throw new EntityNotValidException("Token is not valid");
		}

		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();

		if ((verificationToken.getExpirationTime().getTime()
				- cal.getTime().getTime()) <= 0) {
			emailVerificationTokenRepository.delete(verificationToken);

			throw new EntityNotValidException("Token is expired");
		}
		user.setEnabled(true);
		userRepository.save(user);
		emailVerificationTokenRepository.delete(verificationToken);
		log.trace(String.valueOf(verificationToken));
	}

	public EmailVerificationToken generateNewVerificationToken(String oldToken) {
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
		return userRepository.findByEmailIgnoreCaseAndDeleted(email, false);
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

	public void resendVerificationToken(final String token) {
		EmailVerificationToken emailVerificationToken
				= generateNewVerificationToken(token);
		if (emailVerificationToken == null) {
			throw new EntityNotValidException("Invalid token!");
		}

		User user = emailVerificationToken.getUser();
		mailProduceService.sendEmailVerification(user, emailVerificationToken.getToken());
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
			throw new NotFoundException("User with this email does not exist!");
		}

	}

	//	public Optional<User> getCurrentUser() {
	//
	//	}

}
