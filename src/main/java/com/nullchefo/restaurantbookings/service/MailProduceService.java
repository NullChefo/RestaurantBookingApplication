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

import static com.nullchefo.restaurantbookings.utils.StaticContent.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.nullchefo.restaurantbookings.entity.ContactFormSubmission;
import com.nullchefo.restaurantbookings.entity.MailList;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.repository.MailListRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailProduceService {


	private final static String EMAIL_VERIFICATION_TEMPLATE_NAME = "email_verification";
	private final static String EMAIL_VERIFICATION_TEMPLATE_SUBJECT = "Email verification for " + PROJECT_NAME;
	private final static String PASSWORD_RESET_TEMPLATE_NAME = "password_reset";
	private final static String PASSWORD_RESET_TEMPLATE_SUBJECT = "Reset your password for " + PROJECT_NAME;
	private final static String CONTACT_FORM_TEMPLATE_NAME = "contact_form_submission";
	private final static String CONTACT_FORM_TEMPLATE_SUBJECT = "We received successfully your submission";
	private final static String LOGIN_MAIL_TEMPLATE_NAME = "login_mail";
	private final static String LOGIN_MAIL_TEMPLATE_SUBJECT = "Did you logged-in in " + PROJECT_NAME;
	private final String validateRegistrationURL;
	private final String passwordResetURL;
	private final MailListRepository mailListRepository;
	private final MailSenderService mailSenderService;

	public MailProduceService(

			@Value("${address.front_end.validate_registration_url}") final String validateRegistrationURL,
			@Value("${address.front_end.password_reset_url}") final String passwordResetURL,
			final MailListRepository mailListRepository, MailSenderService mailSenderService) {

		this.validateRegistrationURL = validateRegistrationURL;
		this.passwordResetURL = passwordResetURL;
		this.mailListRepository = mailListRepository;
		this.mailSenderService = mailSenderService;
	}

	public void sendEmailVerification(User user, String token) {


		String url =
				validateRegistrationURL
						+ "/"
						+ token;

		Context context = setDefaultContext();

		context.setVariable("action_url", url);
		context.setVariable("user_first_name", user.getFirstName());

		// String to, String subject, String templateName, Context context
		mailSenderService.sendEmail(
				user,
				EMAIL_VERIFICATION_TEMPLATE_SUBJECT,
				EMAIL_VERIFICATION_TEMPLATE_NAME,
				context);

		log.info(
				"Click the link to email verify: {}",
				url);

		log.trace("Click the link to email verify: {}",
				  url);

		saveToMailListStatisticsEntity(user);

	}

	private void saveToMailListStatisticsEntity(final User user) {
		MailList mailList = mailListRepository.findByUser(user);

		if (mailList == null) {
			mailList = new MailList();
			mailList.setUser(user);
			mailListRepository.save(mailList);
		}
		if (mailList.getSentMailsForUser() == null) {
			mailList.setSentMailsForUser(1);
		} else {
			mailList.setSentMailsForUser(mailList.getSentMailsForUser() + 1);
		}

		mailListRepository.save(mailList);
	}

	public void passwordResetTokenMail(User user, String token) {


		String url =
				passwordResetURL
						+ "/"
						+ token;

		Context context = setDefaultContext();
		context.setVariable("action_url", url);
		context.setVariable("user_first_name", user.getFirstName());

		mailSenderService.sendEmail(user, PASSWORD_RESET_TEMPLATE_SUBJECT, PASSWORD_RESET_TEMPLATE_NAME, context);

		log.info(
				"Click the link to Reset your Password: {}",
				url);

		saveToMailListStatisticsEntity(user);

	}
	public void sendLoginMail(User user, String ipAddress) {
		Context context = setDefaultContext();
		context.setVariable("user_ip", ipAddress);
		context.setVariable("action_url", passwordResetURL);
		context.setVariable("user_first_name", user.getFirstName());
		context.setVariable("login-time", getCurrentFormattedDate());

		mailSenderService.sendEmail(user, PASSWORD_RESET_TEMPLATE_SUBJECT, PASSWORD_RESET_TEMPLATE_NAME, context);
	}

	private String getCurrentFormattedDate() {
		LocalDateTime currentDate = LocalDateTime.now(ZoneOffset.UTC);
		DateTimeFormatter myFormatObject = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
		return currentDate.format(myFormatObject);
	}

	private Context setDefaultContext() {
		Context context = new Context();

		context.setVariable("project_name", PROJECT_NAME);
		context.setVariable("project_url", PROJECT_URL);
		context.setVariable("project_name", PROJECT_NAME);
		context.setVariable("thanks_from_name", THANKS_FROM_NAME);
		context.setVariable("organization_address", ORGANISATION_ADDRESS);
		context.setVariable("organisational_extra_info", ORGANISATIONAL_EXTRA_INFO);
		context.setVariable("organization_name", ORGANISATION_NAME);

		return context;
	}

	public void sendContactFormEmail(final ContactFormSubmission submission) {

		Context context = setDefaultContext();
		context.setVariable("user_name", submission.getName());
		context.setVariable("user_massage",submission.getMessage());
		context.setVariable("user_reason",submission.getReasonForSubmission());

		// TODO fix this more elegant
		User user = new User();
		user.setEmail(submission.getEmail());

		mailSenderService.sendEmail(user, CONTACT_FORM_TEMPLATE_SUBJECT, CONTACT_FORM_TEMPLATE_NAME, context);

	}
}
