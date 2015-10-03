package edu.unsw.comp9321.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
			//closeConnection(); has to be closed outside this method otherwise ResultSet is not accessible
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

	//checks whether supplied admin login details are correct
	public boolean validAdmin(AdminDTO adminLogin){
		String id = adminLogin.getId();
		String password = adminLogin.getPassword();
		String query = "select count(*) AS matchcount from admin where id = '" + id + "' AND password = '" + password + "'";
		ResultSet rs = queryDatabase(query);
		boolean retVal = false;

		try {
			rs.last();
			if (rs.getInt("matchcount") == 1) {
				retVal = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
		return retVal;
	}

	public UserDTO getUserDTO(String username) {
		UserDTO userDTO = null;
		
		if (isBuyer(username)) {
			//System.out.println("Found buyer!");
			userDTO = new BuyerDTO();
		} else if (isSeller(username)) {
			//System.out.println("Found seller!");
			userDTO = new SellerDTO();
		} else {
			System.out.println("Neither buyer nor seller...");
			userDTO = new UserDTO();
		}
		
		String query = "select * from people where username = '" + username + "'";
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				//pull all information out of results and put it into userDTO
				
				userDTO.setUsername(rs.getString("username"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setEmail(rs.getString("email"));
				userDTO.setFirstName(rs.getString("first_name"));
				userDTO.setLastName(rs.getString("last_name"));
				userDTO.setBirthYear(rs.getInt("birth_year"));
				userDTO.setAddress(rs.getString("address"));
				userDTO.setBan(rs.getBoolean("ban"));
				userDTO.setAccountActivated(rs.getBoolean("account_activated"));
			
				// this is a bit hacky but will leave it for now
				
				query = "select * from buyers where buyer_id = '" + username + "'";
				ResultSet rs_specific = queryDatabase(query);
				if (rs_specific.next()) {
					BuyerDTO buyerDTO = (BuyerDTO) userDTO;
					buyerDTO.setCreditCard(rs.getLong("credit_card"));
					userDTO = buyerDTO;
				}
				rs_specific.close();
				
				query = "select * from sellers where seller_id = '" + username + "'";
				rs_specific = queryDatabase(query);
				if (rs_specific.next()) {
					SellerDTO sellerDTO = (SellerDTO) userDTO;
					sellerDTO.setPaypal(rs.getString("paypal"));
					closeConnection();
					userDTO = sellerDTO;
				}
				
				rs_specific.close();
				rs.close();
				closeConnection();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDTO;
	}
	
	public boolean userExistsHelper(String username, String table, String column) {
		String query = "select * from " + table + " where " + column + " = '" + username + "'";
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				closeConnection();
				return true;
			} else {
				closeConnection();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isBuyer(String username) {
		return userExistsHelper(username, "buyers", "buyer_id");
	}
	
	public boolean isSeller(String username) {
		return userExistsHelper(username, "sellers", "seller_id");
	}	

	public void updateUserBan(UserDTO userDTO, boolean banStatus) {
		String username = userDTO.getUsername();
		String banStr = "false";
		if (banStatus) {
			banStr = "true";
		}
		String query = "update people set ban = " + banStr + " where username = '" + username + "'";
		updateDatabase(query);
	}
	
	/**
	 * Test if the username exists in the users table in the database
	 * 
	 * @param username
	 */
	private boolean userExists(String username) {
		String query = "select count(*) AS matchcount from people where username = '" + username + "'";
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
		String query = "select * from people where username='" + username + "' and "
					 + "password='" + password + "'" + " and account_activated=true" 
					 + " and ban=false";
		//System.out.println("query = " + query);
		UserDTO user = null;
		
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				user = getUserDTO(username);
			}
			rs.close();
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
		String query = "update people set account_activated=true where username='" + username + "'";
		updateDatabase(query);

		return true;
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
					"insert into people (username, password, email, first_name, last_name, birth_year, "
							+ "address, account_activated, ban) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getFirstName());
			stmt.setString(5, user.getLastName());
			stmt.setLong(6, user.getBirthYear());
			stmt.setString(7, "temp address");
			stmt.setBoolean(8, false);
			stmt.setBoolean(9, false);

			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataAccessException("Did not insert one row into database");
			
			if (user instanceof BuyerDTO) {
				BuyerDTO buyer = (BuyerDTO) user;
				PreparedStatement stmt_buyer = con.prepareStatement(
						"insert into buyers (buyer_id, credit_card) values (?, ?)");
				stmt_buyer.setString(1, buyer.getUsername());
				stmt_buyer.setLong(2, buyer.getCreditCard());
				stmt_buyer.executeUpdate();
				con.close();
			} else if (user instanceof SellerDTO) {
				SellerDTO seller = (SellerDTO) user;
				PreparedStatement stmt_seller = con.prepareStatement(
						"insert into sellers (seller_id, paypal) values (?, ?)");
				stmt_seller.setString(1, seller.getUsername());
				stmt_seller.setString(2, seller.getPaypal());
				stmt_seller.executeUpdate();
				con.close();
			}
			
			
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
	
	
	public boolean updateUser(UserDTO user) {
		
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement(
			"UPDATE people SET password = ?, email = ?, first_name = ?, last_name = ?"
			+ ", birth_year = ?, address = ? WHERE username = ?");
			stmt.setString(1,user.getPassword());
			stmt.setString(2,user.getEmail());
			stmt.setString(3,user.getFirstName());
			stmt.setString(4,user.getLastName());
			stmt.setInt(5,user.getBirthYear());
			stmt.setString(6,user.getAddress());
			stmt.setString(7,user.getUsername());
			stmt.executeUpdate();
			stmt.close();
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return false;
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

	public ArrayList<PublicationDTO> searchPublications (String title) {
		ArrayList<PublicationDTO> publications = new ArrayList<PublicationDTO>();
		String query = "";
		if(title == null || title.equals("")) {
			query = "select * from publications";
		} else {
			query = "select * from publications where lower(title) like lower('%" + title + "%')";
		}
		ResultSet rs = queryDatabase(query);
		try {
			while(rs.next()) {
				PublicationDTO p = createPublication(rs);
				publications.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		closeConnection();
		return publications;
	}
	
	private PublicationDTO createPublication(ResultSet rs) throws SQLException {
		PublicationDTO publ = new PublicationDTO();
		publ.setTitle(rs.getString("title"));
		publ.setPrice(rs.getInt("price"));
		publ.setAuthor(rs.getString("author"));
		publ.setPubType(rs.getString("pub_type"));
		publ.setPubYear(rs.getInt("pub_year"));
		publ.setIsbn(rs.getString("isbn"));
		publ.setPicture(rs.getString("picture"));
		publ.setPause(rs.getBoolean("pause"));
		publ.setSellerId(rs.getString("seller_id"));
		return publ;
	}
}
