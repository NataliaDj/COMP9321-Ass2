package edu.unsw.comp9321.jdbc;

import java.io.Serializable;

public class UserDTO implements Serializable  {
	
	private String username;
	private String password; 
	private String email;
	private String firstName;
	private String lastName;
	private int birthYear; 
	private String[] address; 
	private boolean accountActivated;
	private boolean ban;
	private BuyerDTO buyerDTO;
	private SellerDTO sellerDTO;
	
	public UserDTO() {
		username = "NULL";
		address = new String[6];
	}
	
	/*public String getUserType() {
		return "need to override method in child class";
	}*/
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	
	public String getAddressOne() {
		return address[0];
	}
	
	public String getAddressTwo() {
		return address[1];
	}

	public String getAddressCity() {
		return address[2];
	}
	
	public String getAddressPostalCode() {
		return address[3];
	}
	
	public String getAddressState() {
		return address[4];
	}
	
	public String getAddressCountry() {
		return address[5];
	}
	
	public void setAddress(String full_address) {
		address = full_address.split(",");
	}
	
	public String getAddressString() {
		String returnString = address[0];
		for (int i = 1; i < 6; i++) {
			returnString += "," + address[i];
		}
		return returnString;
	}

	public boolean getAccountActivated() {
		return accountActivated;
	}
	public void setAccountActivated(boolean accountActivated) {
		this.accountActivated = accountActivated;
	}
	public boolean getBan() {
		return ban;
	}
	public void setBan(boolean ban) {
		this.ban = ban;
	}
	
	public boolean hasBuyerDTO() {
		if (buyerDTO != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public BuyerDTO getBuyerDTO() {
		return buyerDTO;
	}
	
	public void setBuyerDTO(BuyerDTO buyerDTO) {
		this.buyerDTO = buyerDTO;
	}
	
	public boolean hasSellerDTO() {
		if (sellerDTO != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public SellerDTO getSellerDTO() {
		return sellerDTO;
	}
	
	public void setSellerDTO(SellerDTO sellerDTO) {
		this.sellerDTO = sellerDTO;
	}
}
