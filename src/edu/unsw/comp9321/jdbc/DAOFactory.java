package edu.unsw.comp9321.jdbc;

import java.util.HashMap;
import java.util.Map;


/**
 *	Added this class to setup initaliziation of database DAO
 *
 * @author  yunki, Vincent
 */
public class DAOFactory {
	
	private static final String USER_DAO = "userDAO";
	private static final String BOOKSTORE_DAO = "bookstoreDAO";
	
	private Map daos;
	
	private static DAOFactory instance = new DAOFactory();
	
	/** Creates a new instance of DAOFactory */
	private DAOFactory() {
		daos = new HashMap();
		//daos.put(USER_DAO, new UserDAO());
		daos.put(BOOKSTORE_DAO, new BookStoreDAO());
	}
	
	/**
	 * Finds the user dao
	 * @return
	 */
	//public UserDAO getUserDAO() {
	//	return (UserDAO) daos.get(USER_DAO);
	//}

	/**
	 * Finds the user dao
	 * @return
	 */
	public BookStoreDAO getBookStoreDAO () {
		return (BookStoreDAO) daos.get(BOOKSTORE_DAO);
	}
	
	public static DAOFactory getInstance() {
		return instance;
	}
}
