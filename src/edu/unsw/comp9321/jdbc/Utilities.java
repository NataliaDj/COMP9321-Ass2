package edu.unsw.comp9321.jdbc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Utilities {
	
	
	/**
	 * Used to send an email with help of JavaMail
	 * 
	 * Followed tutorial here: https://tomcat.apache.org/tomcat-7.0-doc/jndi-resources-howto.html#JavaMail_Sessions
	 * 
	 * API can be downloaded from here: http://www.oracle.com/technetwork/java/javamail/javamail145-1904579.html
	 * has to be placed in tomcat lib folder (not project, but tomcat system folder)
	 * 
	 * @param email_sender
	 * @param email_receiver
	 * @param subject
	 * @param content
	 */
	public static void sendMail(String email_sender, String email_receiver,
								String subject, String content) {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			Session session = (Session) envCtx.lookup("mail/Session");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email_sender));
			InternetAddress to[] = new InternetAddress[1];
			to[0] = new InternetAddress(email_receiver);
			message.setRecipients(Message.RecipientType.TO, to);
			message.setSubject(subject);
			message.setContent(content, "text/plain");
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 
	 * @param unhashed
	 * @return
	 */
	public static String generateMD5(String unhashed) {
        String generated_md5 = null;
        if (unhashed == null) return null;
         
        try {
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        digest.update(unhashed.getBytes(), 0, unhashed.length());
	        generated_md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generated_md5;
    }
	
	
}
