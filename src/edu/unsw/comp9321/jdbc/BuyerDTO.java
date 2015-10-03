package edu.unsw.comp9321.jdbc;

import java.io.Serializable;

public class BuyerDTO implements Serializable{
	private long creditCard;
	
	public String getUserType() {
		return "buyer";
	}
	
	public long getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(long creditCard) {
		this.creditCard = creditCard;
	}
	
}
