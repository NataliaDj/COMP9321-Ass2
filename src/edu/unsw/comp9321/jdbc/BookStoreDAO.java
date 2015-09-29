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
		
	
	/**
	 * Query the database and return a result set 
	 * Use updateDatabase() for update statements and alter statements
	 * 
	 * Need to close connection after with closeConnection() because otherwise
	 * ResultSet closes and it can't be used
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
            closeConnection();
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
	 * Test if username and password combination exists in the database, 
	 * return UserDTO if found, null otherwise
	 * 
	 * Also NEED to check if account is activated
	 * 
	 * @param username
	 */
	public UserDTO userLogin(String username, String password) {
		String query = "select * from users where username='" + username + "' and "
					 + "password='" + password + "'";
		//System.out.println("query = " + query);
		UserDTO user = null;
		
		ResultSet rs = queryDatabase(query);
		try {
			while(rs.next()) {
				String username_result = rs.getString("username");
				if (username_result.equals(username)) {
					user = new UserDTO();
					user.setUsername(username);
					user.setPassword(password);
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setBirthYear(rs.getInt("birth_year"));
					user.setEmail(rs.getString("email"));
					user.setAddress(rs.getString("address"));
					user.setCreditCard(rs.getInt("credit_card"));
					break;
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null; // means there is no result
		}
		
		closeConnection();
		return user;
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
	
	/**
	 * 
	 * @param pub
	 * @return
	 */
	public boolean newBookListing(PublicationDTO pub) {
		try {
			//create connection and prepare a query statement
		     con = services.createConnection();
		     PreparedStatement stmt = con.prepareStatement(
		       "insert into publications (id, title, price, author, pub_type, pub_year, "
		       + "isbn, picture, pause, seller_id) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		    
		     //fill in the query statement
		     stmt.setString(2, pub.getTitle());
		     stmt.setInt(3, pub.getPrice());
		     stmt.setString(4, pub.getAuthor());
		     stmt.setString(5, pub.getPubType());
		     stmt.setInt(6, pub.getPubYear());
		     stmt.setString(7, pub.getIsbn());
		     stmt.setString(8, pub.getPicture());
		     if(pub.isPause()) {
		    	 stmt.setBoolean(9, true);
		     } else {
		    	 stmt.setBoolean(9, false);
		     }
		     stmt.setString(10, pub.getSeller());
		    	
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
