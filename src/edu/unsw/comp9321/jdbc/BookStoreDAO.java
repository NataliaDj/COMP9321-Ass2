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
	}
}
