package edu.unsw.comp9321.jdbc;

public class BuyerDTO extends UserDTO {
	private long creditCard;
	
	public long getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(long creditCard) {
		this.creditCard = creditCard;
	}
}
