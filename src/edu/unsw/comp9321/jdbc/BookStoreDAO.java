package edu.unsw.comp9321.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import edu.unsw.comp9321.exception.DataAccessException;
import edu.unsw.comp9321.exception.ServiceLocatorException;

public class BookStoreDAO {

	static Logger logger = Logger.getLogger(BookStoreDAO.class.getName());
	private DBConnectionFactory services;

	public BookStoreDAO() {
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
		
		logger.info("Got connection");
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
	
	public void addUser(UserDTO bean) throws DataAccessException {
		   Connection con = null;
		   try {
		     con = services.createConnection();
		     PreparedStatement stmt = con.prepareStatement(
		       "insert into users (username, password, email, nickname, first_name, last_name, birth_year, "
		       + "address, credit_card) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		     stmt.setString(1, bean.getUsername());
		     stmt.setString(2, bean.getPassword());
		     stmt.setString(3, bean.getEmail());
		     stmt.setString(4, bean.getNickname());
		     stmt.setString(5, bean.getFirstName());
		     stmt.setString(6, bean.getLastName());
		     stmt.setLong(7, bean.getBirthYear());
		     stmt.setString(8, "");
		     //stmt.setString(9, bean.getAddressOne());
		     //stmt.setString(10, bean.getAddressTwo());
		     //stmt.setString(11, bean.getCity());
		     //stmt.setString(12, bean.getPostalCode());
		     //stmt.setString(13, bean.getState());
		     //stmt.setString(14, bean.getCountry());
		     stmt.setLong(9, bean.getCreditCard());
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
		}
}
