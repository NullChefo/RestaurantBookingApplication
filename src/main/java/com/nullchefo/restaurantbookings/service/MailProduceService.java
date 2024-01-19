package com.nullchefo.restaurantbookings.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.nullchefo.restaurantbookings.entity.MailList;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.repository.MailListRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailProduceService {

	private final static String PROJECT_NAME = "Masters Degree project";
	private final static String PROJECT_URL = "https://nullchefo.com/uni";
	private final static String THANKS_FROM_NAME = "Stefan Kehayov";
	private final static String ORGANISATION_NAME = "Plovdiv University \"Paisii Hilendarski\"";
	private final static String ORGANISATIONAL_EXTRA_INFO = "Faculty of mathematics and informatics";
	private final static String ORGANISATION_ADDRESS = "4700 bul. Bulgaria 256";
	private final static String SUPPORT_EMAIL = "support@nullchefo.com";
	private final static String EMAIL_VERIFICATION_TEMPLATE_NAME = "email_verification";
	private final static String EMAIL_VERIFICATION_TEMPLATE_SUBJECT = "Email verification for " + PROJECT_NAME;
	private final static String PASSWORD_RESET_TEMPLATE_NAME = "password_reset";
	private final static String PASSWORD_RESET_TEMPLATE_SUBJECT = "Reset your password for " + PROJECT_NAME;
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

		MailList mailList = mailListRepository.findByUser(user);

		if (mailList == null) {
			mailList = new MailList();
			mailList.setUser(user);
			mailListRepository.save(mailList);
		}

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

		if (mailList.getSentMailsForUser() == null) {
			mailList.setSentMailsForUser(1);
		} else {
			mailList.setSentMailsForUser(mailList.getSentMailsForUser() + 1);
		}

		mailListRepository.save(mailList);

	}

	public void passwordResetTokenMail(User user, String token) {
		MailList mailList = mailListRepository.findByUser(user);

		if (mailList == null) {
			mailList = new MailList();
			mailList.setUser(user);
			mailListRepository.save(mailList);
		}

		final String templateName = "password_reset";

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

		if (mailList.getSentMailsForUser() == null) {
			mailList.setSentMailsForUser(1);
		} else {
			mailList.setSentMailsForUser(mailList.getSentMailsForUser() + 1);
		}
		mailListRepository.save(mailList);

	}

	// TODO use
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

}
