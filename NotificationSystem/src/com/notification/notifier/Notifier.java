package com.notification.notifier;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Notifier {

	public void sendNotification(String mailId,String notification){
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("happywork24@gmail.com","#fpa123#$");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("happywork24@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailId));
			message.setSubject("Update ");
			message.setText("notification");

			Transport.send(message);

			System.out.println("Sent notification to - "+mailId+" -Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
