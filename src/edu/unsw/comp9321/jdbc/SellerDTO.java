package edu.unsw.comp9321.jdbc;

public class SellerDTO extends UserDTO {
	private String paypal;
	
	public String getUserType() {
		return "seller";
	}
	
	public String getPaypal() {
		return paypal;
	}
	
	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}
}
