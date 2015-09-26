package edu.unsw.comp9321.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import edu.unsw.comp9321.exception.DataAccessException;
import edu.unsw.comp9321.exception.ServiceLocatorException;

public class BookStoreDAO {

	private DBConnectionFactory services;
	Connection con = null;
	
	public BookStoreDAO() {
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
		
	}
	
	/*public void addUser(UserDTO u) throws DataAccessException {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("insert into users (username, password, email) values (?, ?, ?)");
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getEmail());
			int n = stmt.executeUpdate();
			if (n != 1) {
				throw new DataAccessException(
						"Did not insert one row into database");
			}
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		}
	}*/
	
	
	
	/**
	 * Query the database and return a result set 
	 * Use updateDatabase() for update statements and alter statements
	 * Need to close connection after with closeConnection()
	 * 
	 * @param query
	 * @return
	 */
	private ResultSet queryDatabase(String query) {
		con = null;
		try {
			con = services.createConnection();
			Statement statement = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = statement.executeQuery(query);
            return rs;
			
	   } catch (ServiceLocatorException e) {
	       throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
	   } catch (SQLException e) {
	       throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
	   } 
	}
	
	/**
	 * Update the database with update, alter, etc. statements
	 * Use queryDatabase() for queries
	 * Need to close connection after with closeConnection()
	 * 
	 * @param query
	 */
	private void updateDatabase(String query) {
		con = null;
		try {
			con = services.createConnection();
			Statement statement = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            statement.executeUpdate(query);
			
	   } catch (ServiceLocatorException e) {
	       throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
	   } catch (SQLException e) {
	       throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
	   }
	}
	
	private void closeConnection() {
		if (con != null) {
			try {
	        	con.close();
	        } catch (SQLException e1) {
	        	e1.printStackTrace();
	        }
	    }
	}
	
	/**
	 * Test if the username exists in the users table in the database
	 * 
	 * @param username
	 */
	private boolean userExists(String username) {
		String query = "select count(*) AS matchcount from users where username = '" + username + "'";
		int matches = 0;
		
		ResultSet rs = queryDatabase(query);
		try {
			rs.last();
			matches = rs.getInt("matchcount");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
        
		if (matches > 0) { 
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * 
	 * @param username
	 * @return
	 */
	public boolean activateUser(String username) {
		System.out.println("Activating database: " + username + "!");
		
		String query = "update users set account_activated=1 where username='" + username + "'";
		updateDatabase(query);
		
		return false;
	}
	
	/**
	 * Add user to the database and return true
	 * If username already exists, return false
	 * 
	 * @param user
	 * @return
	 * @throws DataAccessException
	 */
	public boolean addUser(UserDTO user) throws DataAccessException {
		
		// first test if user already exists
		if (userExists(user.getUsername())) {
			System.out.println("Not creating user because username already exists!");
			return false;
		} else {
			System.out.println("creating user = " + user.getUsername());
		}
	
	   Connection con = null;
	   try {
	     con = services.createConnection();
	     PreparedStatement stmt = con.prepareStatement(
	       "insert into users (username, password, email, first_name, last_name, birth_year, "
	       + "address, credit_card) values (?, ?, ?, ?, ?, ?, ?, ?)");
	    
	     stmt.setString(1, user.getUsername());
	     stmt.setString(2, user.getPassword());
	     stmt.setString(3, user.getEmail());
	     stmt.setString(4, user.getFirstName());
	     stmt.setString(5, user.getLastName());
	     stmt.setLong(6, user.getBirthYear());
	     stmt.setString(7, "temp address");
	     //stmt.setString(9, bean.getAddressOne());
	     //stmt.setString(10, bean.getAddressTwo());
	     //stmt.setString(11, bean.getCity());
	     //stmt.setString(12, bean.getPostalCode());
	     //stmt.setString(13, bean.getState());
	     //stmt.setString(14, bean.getCountry());
	     stmt.setLong(8, user.getCreditCard());
	    	
	     int n = stmt.executeUpdate();
	     if (n != 1)
	       throw new DataAccessException("Did not insert one row into database");
	   } catch (ServiceLocatorException e) {
	       throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
	   } catch (SQLException e) {
	       throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
	   } finally {
	      if (con != null) {
	         try {
	           con.close();
	         } catch (SQLException e1) {
	           e1.printStackTrace();
	         }
	      }
	   }
	   
	   return true;
	}
}
