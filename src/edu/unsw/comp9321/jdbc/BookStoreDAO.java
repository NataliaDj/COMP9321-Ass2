package edu.unsw.comp9321.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
	public ResultSet queryDatabase(String query) {
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
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
		} finally {
			closeConnection();
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
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}
		return retVal;
	}

	public UserDTO getUserDTO(String username) {
		UserDTO userDTO = new UserDTO();
		
		String query = "select * from people where username = '" + username + "'";
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				//pull all information out of results and put it into userDTO
				
				userDTO.setUsername(rs.getString("username"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setEmail(rs.getString("email"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setFirstName(rs.getString("first_name"));
				userDTO.setLastName(rs.getString("last_name"));
				userDTO.setBirthYear(rs.getInt("birth_year"));
				userDTO.setAddress(rs.getString("address"));
				userDTO.setBan(rs.getBoolean("ban"));
				userDTO.setAccountActivated(rs.getBoolean("account_activated"));
				userDTO = GetBuyerDTO(userDTO);
				userDTO = GetSellerDTO(userDTO);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}
		return userDTO;
	}
	
	public List<CartEntryDTO> getCartDTO(String username) {
		List<CartEntryDTO> cart = new LinkedList<CartEntryDTO>();
		String query = "select * from shopping_cart where buyer_key = '"+ username +"'";
		ResultSet rs = queryDatabase(query);
		try {
			while (rs.next()) {
				CartEntryDTO current = new CartEntryDTO();
				current.setUsername(username);
				current.setAdded(rs.getDate("added"));
				current.setRemoved(rs.getDate("removed"));
				current.setPurchased(rs.getDate("purchased"));
				
				//create and add publicationDTO
				ResultSet temp = queryDatabase("select * from publications where id = " + rs.getInt("publication_key"));
				temp.next();
				PublicationDTO pub = createPublication(temp);
				current.setPublication(pub);
				
				cart.add(current);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		
		return cart;
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
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}
	
	public boolean isBuyer(String username) {
		return userExistsHelper(username, "buyers", "buyer_id");
	}
	
	public boolean isSeller(String username) {
		return userExistsHelper(username, "sellers", "seller_id");
	}	

	public void removePublication(int id) {
		String query = "delete from shopping_cart where publication_key = " + id;
		updateDatabase(query);
		query = "delete from publications where id = " + id;
		updateDatabase(query);
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
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}

		if (matches > 0) { 
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Test if the username exists in the users table in the database
	 * 
	 * @param username
	 */
	private boolean emailExists(String email) {
		String query = "select count(*) AS matchcount from people where email = '" + email + "'";
		int matches = 0;

		ResultSet rs = queryDatabase(query);
		try {
			rs.last();
			matches = rs.getInt("matchcount");
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			closeConnection();
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
		UserDTO user = null;
		
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				user = getUserDTO(username);
			}
 		} catch (SQLException e) {
			e.printStackTrace();
			return null; // means there is no result
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}
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
		if (userExists(user.getUsername()) || emailExists(user.getEmail())) {
			System.out.println("Not creating user because username and/or email already exists!");
			return false;
		} else {
			System.out.println("creating user = " + user.getUsername());
		}

		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement(
					"insert into people (username, password, email, nickname, first_name, last_name, birth_year, "
							+ "address, account_activated, ban) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getNickname());
			stmt.setString(5, user.getFirstName());
			stmt.setString(6, user.getLastName());
			stmt.setLong(7, user.getBirthYear());
			stmt.setString(8, user.getAddressString());
			stmt.setBoolean(9, false);
			stmt.setBoolean(10, false);

			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataAccessException("Did not insert one row into database");
			stmt.close();
			con.close();
			
			
			
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
		
		insertBuyersAndSellers(user);
		return true;
	}
	
	private UserDTO GetBuyerDTO(UserDTO user) {
		String query = "select * from buyers where buyer_id = '" + user.getUsername() + "'";
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				BuyerDTO buyer = new BuyerDTO();
				buyer.setCreditCard(rs.getLong("credit_card"));
				user.setBuyerDTO(buyer);
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
		return user;
	}
	
	private UserDTO GetSellerDTO(UserDTO user) {
		String query = "select * from sellers where seller_id = '" + user.getUsername() + "'";
		ResultSet rs = queryDatabase(query);
		try {	
			if (rs.next()) {
				SellerDTO seller = new SellerDTO();
				seller.setPaypal(rs.getString("paypal"));
				user.setSellerDTO(seller);
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		closeConnection();
		return user;
	}
	
	
	private void insertBuyersAndSellers(UserDTO user) {
		Connection con = null;
		try {
				con = services.createConnection();
			if (user.hasBuyerDTO()) {
				BuyerDTO buyer = user.getBuyerDTO();
				PreparedStatement stmt_buyer = con.prepareStatement(
						"insert into buyers (buyer_id, credit_card) values (?, ?)");
				stmt_buyer.setString(1, user.getUsername());
				stmt_buyer.setLong(2, buyer.getCreditCard());
				stmt_buyer.executeUpdate();
				stmt_buyer.close();
			} 
			
			if (user.hasSellerDTO()) {
				SellerDTO seller = user.getSellerDTO();
				PreparedStatement stmt_seller = con.prepareStatement(
						"insert into sellers (seller_id, paypal) values (?, ?)");
				stmt_seller.setString(1, user.getUsername());
				stmt_seller.setString(2, seller.getPaypal());
				stmt_seller.executeUpdate();
				stmt_seller.close();
			}
			
			con.close();
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
	
	private void updateBuyersAndSellers(UserDTO user) {
		Connection con = null;
		try {
			con = services.createConnection();
			if (user.hasBuyerDTO()) {
				PreparedStatement stmt = con.prepareStatement(
				"UPDATE buyers SET credit_card = ? WHERE buyer_id = ?");
				stmt.setLong(1,user.getBuyerDTO().getCreditCard());
				stmt.setString(2,user.getUsername());
				stmt.executeUpdate();
				stmt.close();
			} 
			
			if (user.hasSellerDTO()) {
				PreparedStatement stmt = con.prepareStatement(
				"UPDATE sellers SET paypal = ? WHERE seller_id = ?");
				stmt.setString(1,user.getSellerDTO().getPaypal());
				stmt.setString(2,user.getUsername());
				stmt.executeUpdate();
				stmt.close();
			} 
			
			con.close();
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
	
	
	public boolean updateUser(UserDTO user) {
		updateBuyersAndSellers(user);
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement(
			"UPDATE people SET password = ?, email = ?, nickname = ?, first_name = ?, last_name = ?"
			+ ", birth_year = ?, address = ? WHERE username = ?");
			stmt.setString(1,user.getPassword());
			stmt.setString(2,user.getEmail());
			stmt.setString(3,user.getNickname());
			stmt.setString(4,user.getFirstName());
			stmt.setString(5,user.getLastName());
			stmt.setInt(6,user.getBirthYear());
			stmt.setString(7,user.getAddressString());
			stmt.setString(8,user.getUsername());
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
	 * @throws Exception 
	 */
	public boolean newBookListing(PublicationDTO pub) throws Exception {
		try {
			//create connection and prepare a query statement
		     con = services.createConnection();
		     String insert = "insert into publications (title, price, author, pub_type, pub_year, "
				       + "isbn, picture, pause";
		     if (pub.getSeller() != null) {
		    	 insert = insert + ", seller_id";
		     }
		     insert = insert + ") values (";
		     insert = insert + "'" + pub.getTitle() + "', ";
		     insert = insert + pub.getPrice() + ", ";
		     insert = insert + "'" + pub.getAuthor() + "', ";
		     insert = insert + "'" + pub.getPubType() + "', ";
		     insert = insert + pub.getPubYear() + ", ";
		     insert = insert + "'" + pub.getIsbn() + "', ";
		     insert = insert + "'" + pub.getPicture() + "', ";
		     insert = insert + "'" + pub.isPause() + "', ";
		     if (pub.getSeller() != null || pub.getSeller() != "") {
		    	 insert = insert + "'" + pub.getSeller() + "')";
		     }
		     updateDatabase(insert);
		   } catch (Exception e) {
			   throw e;
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
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}
		return listings;
	}
	
	public void updatePause(String id) {
		String query ="select * from publications where id=" + id;
		
		String pause = "true";
		ResultSet rs = queryDatabase(query);
		try {
			while (rs.next()) {
				if(rs.getBoolean("pause")) {
					pause = "false";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		query = "update publications set pause = " + pause + " where id = " + id;
		updateDatabase(query);
	}
	
	public ArrayList<PublicationDTO> getCartItems(String username) {
		ArrayList<PublicationDTO> items = new ArrayList<PublicationDTO>();
		String query ="select * from shopping_cart where buyer_key='" + username + "'"
				+ "and removed is null and purchased is null";

		ResultSet rs = queryDatabase(query);
		try {
			while(rs.next()) {
				int key = rs.getInt("publication_key");
				query = "select * from publications where id = " + key;
				ResultSet rs_pub = queryDatabase(query);
				if (rs_pub.next()) {
					PublicationDTO p = createPublication(rs_pub);
					items.add(p);
				}
				try { rs_pub.close(); } catch (SQLException e) {e.printStackTrace();}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}
		return items;
	}
	
	public void removeFromCart(String id, String user) {
		String query ="select * from shopping_cart where publication_key=" + id + "and buyer_key='" + user + "'";
		
		ResultSet rs = queryDatabase(query);
		try {
			if (rs.next()) {
				Timestamp timestamp = new Timestamp(new Date().getTime());
				query = "update shopping_cart set removed = '" + timestamp + "' where publication_key = " + id;
				try {
					con = services.createConnection();
					Statement statement = con.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					statement.executeUpdate(query);
				} catch (ServiceLocatorException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}
	}
	
	public void checkout(String user) {
		String query ="select * from shopping_cart where buyer_key='" + user +"' and purchased is null and removed is null";
		
		ResultSet rs = queryDatabase(query);
		try {
			while (rs.next()) {
				Timestamp timestamp = new Timestamp(new Date().getTime());
				query = "update shopping_cart set purchased='" + timestamp + "' where buyer_key='" + user + "' and publication_key=" + rs.getInt("publication_key")  ;
				try {
					con = services.createConnection();
					Statement statement = con.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					statement.executeUpdate(query);
					query = "select * from publications where id=" + rs.getInt("publication_key"); 
					ResultSet rs_pub = queryDatabase(query);
					try {
						if (rs_pub.next()) {
							query = "select * from people where username='" + rs_pub.getString("seller_id") + "'"; 
							ResultSet rs_sel = queryDatabase(query);
							try { 
								if (rs_sel.next()) {
								Utilities.sendMail("me@something.com", rs_sel.getString("email"), "Your Book was Just Sold", 
										 "Hi " + rs_sel.getString("first_name") + ",\n\n"
										 		+ rs_pub.getString("title") + " was just sold\n\n"
										 		+ "Cheers");
								}
							} finally {
								try { rs_sel.close(); } catch (SQLException e) {e.printStackTrace();}
							}
						}
					} finally {
						try { rs_pub.close(); } catch (SQLException e) {e.printStackTrace();}
					}
				} catch (ServiceLocatorException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}
	}
	
	public void addToCart(String id, String seller) {
		String query ="select * from publications where id=" + id;
		String insert = "insert into shopping_cart (added, publication_key, buyer_key) values (";
		ResultSet rs = queryDatabase(query);
		try {
			while (rs.next()) {
				Timestamp timestamp = new Timestamp(new Date().getTime());
				insert = insert + "'" + timestamp + "', ";
				insert = insert + id + ", ";
				insert = insert + "'" + seller + "')";
				try {
					con = services.createConnection();
					Statement statement = con.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					statement.executeUpdate(insert);
				} catch (ServiceLocatorException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
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
		} finally {
			try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			closeConnection();
		}
		return publications;
	}
	
	public PublicationDTO createPublication(ResultSet rs) throws SQLException {
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
		publ.setId(rs.getInt("id"));
		return publ;
	}

}
