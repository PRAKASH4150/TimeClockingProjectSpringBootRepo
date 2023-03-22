package com.tracking.timeclocking.util;

import java.io.ByteArrayOutputStream;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    
    public void sendEmailWithAttachment(String to, String subject, String body,ByteArrayOutputStream attachment, String attachmentName){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        DataSource source = new ByteArrayDataSource(attachment.toByteArray(), "application/octet-stream");
        helper.addAttachment(attachmentName,source);
        emailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
}