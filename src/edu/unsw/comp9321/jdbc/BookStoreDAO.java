package edu.unsw.comp9321.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import edu.unsw.comp9321.exception.DataAccessException;
import edu.unsw.comp9321.exception.ServiceLocatorException;

public class BookStoreDAO {

	static Logger logger = Logger.getLogger(BookStoreDAO.class.getName());
	private Connection connection;
	
	public BookStoreDAO() {
		try {
			connection = DBConnectionFactory.getConnection();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.info("Got connection");
	}
	
	public void addUser(UserDTO u) throws DataAccessException {
		try {
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("insert into tbl_contacts (owner_id, short_name, full_name, email, contact_number, notes) values (?, ?, ?, ?, ?, ?)");
			stmt.setInt(1, bean.getOwner());
			stmt.setString(2, bean.getShortName());
			stmt.setString(3, bean.getFullName());
			stmt.setString(4, bean.getEmail());
			stmt.setString(5, bean.getContactNumber());
			stmt.setString(6, bean.getNotes());
			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataAccessException(
						"Did not insert one row into database");
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
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
