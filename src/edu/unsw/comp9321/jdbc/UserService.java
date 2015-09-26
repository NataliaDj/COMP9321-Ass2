package edu.unsw.comp9321.jdbc;

/**
 * This class connects the frontend (e.g. registerCommand) to the database
 * 
 * So for example. RegistrationCommand calls
 */

import java.util.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.mail.Session;


import edu.unsw.comp9321.exception.*;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class UserService {
	private BookStoreDAO bookstoreDAO;

	public UserService() {
		 DAOFactory factory = DAOFactory.getInstance();
		 bookstoreDAO = factory.getBookStoreDAO();
	}

	/**
	 * @see com.enterprise.business.PhonebookService#login(java.lang.String, java.lang.String)
	 */
	public UserDTO login(String username, String password)
		throws UserLoginFailedException {

		UserDTO user = null;
		
          //TODO: this should try to find a UserBean using the UserDAO  
          //TODO: throw LoginFailedException if the user is not found or the operation fails.
          //TODO: if the user is found, return the user
        return user;
	}
	
	/**
	 * 
	 * @param username
	 */
	public void activateUser(String username) {
		
	}
	

	/**
	 * Add the user to the database, send an email confirmation if it's successful
	 * 
	 * @param user 
	 * @return
	 */
	public UserDTO addUser(UserDTO user){
		if (bookstoreDAO.addUser(user)) {
			sendConfirmationEmail(user); // send confirmation email if adding user to database is successful
		}
		return null;
		
	}
	
	
	private void sendConfirmationEmail(UserDTO user) 
	{
		Utilities.sendMail("me@something.com", user.getEmail(), "Website confirmation", 
				 "Hi " + user.getFirstName() + ",\n\n"
				 		+ "Please click the following link to activate your account:\n"
				 		+ "http://localhost:8080/Ass2/ControllerServlet?operation=register&action=activation&"
				 		+ "username=" + user.getUsername() + "\n\n"
				 		+ "Cheers");
	}
	
	

}
