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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nullchefo.restaurantbookings.entity.SentEmail;
import com.nullchefo.restaurantbookings.entity.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailSenderService {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	private final String sender;
	private final SentEmailService sentEmailService;

	@Autowired
	public MailSenderService(
			JavaMailSender mailSender, TemplateEngine templateEngine, @Value("${spring.mail.username}") String sender,
			SentEmailService sentEmailService) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
		this.sender = sender;
		this.sentEmailService = sentEmailService;
	}

	//!!!! Context is the thymeleaf context
	public void sendEmail(User to, String subject, String templateName, Context context) {
		String emailContent = templateEngine.process(templateName, context);

		// Use JavaMailSender to send the email with the processed Thymeleaf template
		// You need to implement this part based on your requirements and the JavaMailSender usage

		MimeMessage mimeMessage
				= mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		SentEmail email = new SentEmail();
		email.setUser(to);
		email.setSubject(subject);
		email.setTemplateName(templateName);
		// TODO can i add the context
		email.setContext(String.valueOf(context));

		try {

			// Setting multipart as true for attachments to send
			mimeMessageHelper
					= new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(to.getEmail());
			mimeMessageHelper.setSubject(
					subject);

			mimeMessageHelper.setText(emailContent, true);

			mailSender.send(mimeMessage);

			email.setSent(true);

		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {
			log.error(e.getMessage());
			email.setSent(false);
		} finally {
			log.info(sentEmailService.create(email).toString());
		}

	}
}
