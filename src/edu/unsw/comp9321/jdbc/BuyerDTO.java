package edu.unsw.comp9321.jdbc;

public class BuyerDTO {
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
