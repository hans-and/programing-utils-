package hasses.magical.tools.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hasses.magical.tools.beans.controller.MailConfig;
import hasses.magical.tools.model.EmailMessage;

@Service
public class EmailSender {

	private static final Logger LOGGER = Logger.getLogger(EmailSender.class);
	
	@Autowired
	private MailConfig mailConfig;

	

	
	public String sendResetEmail(String name,String toEmail, String paramConfirmPswr) throws AddressException, MessagingException {

		LOGGER.debug("Sending email name="+name+" to"+toEmail+ " resettoken:"+paramConfirmPswr);
		Message msg = getMessage();
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
		msg.setSubject(name+" alla glömmer ibland");
		msg.setContent(
				"Följ länken för att återställa https://hasses-magical.club/change?token="+paramConfirmPswr,
				"text/html");
		msg.setSentDate(new Date());
		Transport.send(msg);
		return "Success";
	}

	
	public String sendConfirmEmail(String name,String toEmail, String paramConfirmPswr) throws AddressException, MessagingException {

		Message msg = getMessage();
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
		msg.setSubject(name+" va kul att du vill vara med i Hasses Magiska Klubb");
		msg.setContent(
				"Följ länken för att konfirmera https://hasses-magical.club/confirm?email="+getPlusSafe(toEmail)+"&confpswrd="+paramConfirmPswr,
				"text/html");
		msg.setSentDate(new Date());
		Transport.send(msg);
		return "Success";
	}
	/**
	 * Enables a + sign in email (example hans.andersson+magiskt@gmail.com)
	 * @see https://stackoverflow.com/questions/7842547/request-parameter-losing-plus-sign
	 * 
	 * @param toEmail
	 * @return
	 */
	private String getPlusSafe(String toEmail) {
		return toEmail.replaceAll("+", "%2B");
	}
	private Message getMessage() throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
			}

		}

		);

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mailConfig.getUsername(), false));
		return msg;
	}
	public String sendEmail(EmailMessage message) throws AddressException, MessagingException {

		Message msg = getMessage();
		
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailConfig.getUsername()));
		msg.setSubject(message.getSubject());
		msg.setContent(message.getBody(),"text/html");
		msg.setSentDate(new Date());
		Transport.send(msg);
		return "Success";
	}
}
