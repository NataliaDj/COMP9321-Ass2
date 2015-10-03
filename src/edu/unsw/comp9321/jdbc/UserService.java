package edu.unsw.comp9321.jdbc;

import java.util.ArrayList;

import javax.servlet.http.Cookie;

/**
 * This class connects the frontend (e.g. registerCommand) to the database
 * 
 * So for example. RegistrationCommand calls
 */



import edu.unsw.comp9321.exception.*;

public class UserService {
	private BookStoreDAO bookstoreDAO;

	public UserService() {
		 DAOFactory factory = DAOFactory.getInstance();
		 bookstoreDAO = factory.getBookStoreDAO();
	}

	/**
	 * @see com.enterprise.business.PhonebookService#login(java.lang.String, java.lang.String)
	 */
	public UserDTO login(String username, String password) {
		
		UserDTO user = bookstoreDAO.userLogin(username, password);		
		return user;		
		
	}
	
	
	/**
	 * Called through RegisterCommand when link in email is pressed to activate
	 * 
	 * @param username
	 */
	public void activateUser(String username) {
		if (bookstoreDAO.activateUser(username)) {
			
		} else {
			System.out.println("Couldn't activate user for some reason");
		}
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
	
	/**
	 * Update the users information in the database
	 * 
	 * @param user 
	 * @return
	 */
	public boolean updateUser(UserDTO user){
		return bookstoreDAO.updateUser(user);
				
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
	
	public ArrayList<PublicationDTO> searchPublications(String title) {
		ArrayList<PublicationDTO> results = bookstoreDAO.searchPublications(title);
		return results;
	}

}
