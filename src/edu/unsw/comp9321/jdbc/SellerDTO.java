package edu.unsw.comp9321.jdbc;

import java.io.Serializable;

public class SellerDTO implements Serializable {
	private String paypal;
	
	public String getPaypal() {
		return paypal;
	}
	
	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}
}
