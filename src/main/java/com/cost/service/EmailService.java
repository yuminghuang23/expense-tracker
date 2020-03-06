package com.cost.service;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.cost.model.Email;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private SpringTemplateEngine springTemplateEngine;
	
	public void sendEmail(Email email) {
		try {
			// setting MIME structure
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, 
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			
			// use context and spring template engine to process the html page
			Context context = new Context();
			context.setVariables(email.getModel());
			String html = springTemplateEngine.process("emailTemplate", context);
			
			helper.setTo(email.getTo());
			helper.setFrom(email.getFrom());
			helper.setSubject(email.getSubject());
			helper.setText(html, true);
			
			javaMailSender.send(message);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
