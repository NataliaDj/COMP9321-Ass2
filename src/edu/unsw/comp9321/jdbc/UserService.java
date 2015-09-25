package edu.unsw.comp9321.jdbc;

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
	public UserDTO login(String username, String password)
		throws UserLoginFailedException {

		UserDTO user = null;
		
          //TODO: this should try to find a UserBean using the UserDAO  
          //TODO: throw LoginFailedException if the user is not found or the operation fails.
          //TODO: if the user is found, return the user
        return user;
	}

	public UserDTO addUser(UserDTO user){
		bookstoreDAO.addUser(user);
				
		return null;
	}

}
