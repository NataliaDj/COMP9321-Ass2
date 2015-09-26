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
	 * Test if the username exists in the users table in the database
	 * 
	 * @param username
	 */
	private boolean userExists(String username) {
		Connection con = null;
		int matches = 0;
		try {
			con = services.createConnection();
			Statement statement = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            String query = "select count(*) AS matchcount from users where username = '" + username + "'";
            ResultSet rs = statement.executeQuery(query);
            rs.last();
            
            matches = rs.getInt("matchcount");
			
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
		
		
		if (matches > 0) { 
			return true;
		} else {
			return false;
		}
	}
	
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
