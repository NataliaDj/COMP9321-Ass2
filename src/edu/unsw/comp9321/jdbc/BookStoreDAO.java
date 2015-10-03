package edu.unsw.comp9321.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
			
			return rs;

		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
		} finally {
			try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			};
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
		String query = "select * from people where username = '" + username + "'";
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				//pull all information out of results and put it into userDTO
				userDTO = new UserDTO();
				userDTO.setUsername(rs.getString("username"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setEmail(rs.getString("email"));
				userDTO.setFirstName(rs.getString("first_name"));
				userDTO.setLastName(rs.getString("last_name"));
				userDTO.setBirthYear(rs.getInt("birth_year"));
				userDTO.setAddress(rs.getString("address"));
				userDTO.setBan(rs.getBoolean("ban"));
				int activated = rs.getInt("account_activated");
				if (activated == 1) {				//this would be easier if account_activated were boolean
					userDTO.setAccountActivated(true);
				} else {
					userDTO.setAccountActivated(false);
				}
				
				//then create a BuyerDTO and/or SellerDTO for the account
				BuyerDTO buyerDTO = null;
				SellerDTO sellerDTO = null;
				
				query = "select * from buyers where buyer_id = '" + username + "'";
				rs = queryDatabase(query);
				if (rs.next()) {
					buyerDTO = new BuyerDTO();
					buyerDTO.setCreditCard(rs.getLong("credit_card"));
				}
				
				query = "select * from sellers where seller_id = '" + username + "'";
				rs = queryDatabase(query);
				if (rs.next()) {
					sellerDTO = new SellerDTO();
					sellerDTO.setPaypal(rs.getString("paypal"));
				}
				
				userDTO.setBuyerDTO(buyerDTO);
				userDTO.setSellerDTO(sellerDTO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDTO;
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
					 + "password='" + password + "'";
		//System.out.println("query = " + query);
		UserDTO user = null;
		
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				getUserDTO(username);
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

		String query = "update people set account_activated=1 where username='" + username + "'";
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
			stmt.setLong(8, user.getBuyerDTO().getCreditCard());

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

		     System.out.println("n");
		    
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

	public ArrayList<PublicationDTO> findListing(String username) {
		ArrayList<PublicationDTO> listings = new ArrayList<PublicationDTO>();
		String query ="select * from publications where seller_id='" + username + "'";

		ResultSet rs = queryDatabase(query);
		try {
			while(rs.next()) {
				PublicationDTO p = createPublication(rs);
				listings.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		closeConnection();
		return listings;
	}
	
	public void updatePause(String id) {
		String query ="select * from publications where id=" + id;
		
		String pause = "false";
		ResultSet rs = queryDatabase(query);
		try {
			while (rs.next()) {
				if(rs.getBoolean("pause")) {
					pause = "true";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "update publication set pause = " + pause + " where id = " + id;
		updateDatabase(query);
	}
	
	
	
	public ArrayList<PublicationDTO> getCartItems(String username) {
		ArrayList<PublicationDTO> items = new ArrayList<PublicationDTO>();
		String query ="select * from shopping_cart where buyer_key='" + username + "'" 
				+ "and remove = null and purchased = null"; //check if this is right

		ResultSet rs = queryDatabase(query);
		try {
			while(rs.next()) {
				PublicationDTO p = createPublication(rs);
				items.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		closeConnection();
		return items;
	}
	
	public void removeFromCart(String id) {
		String query ="select * from shopping_cart where publication_key='" + id + "'";
		
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				Timestamp timestamp = new Timestamp(new Date().getTime());
				query = "update shopping_cart set remove = " + timestamp + " where id = " + id;
				updateDatabase(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<PublicationDTO> searchPublications (String title) {
		ArrayList<PublicationDTO> publications = new ArrayList<PublicationDTO>();
		String query = "";
		if(title == null || title.equals("")) {
			query = "select * from publications where pause='false'";
		} else {
			query = "select * from publications where lower(title) like lower('%" + title + "%') and pause='false'";
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
