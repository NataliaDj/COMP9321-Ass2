package edu.unsw.comp9321.jdbc;

import java.io.Serializable;

public class AdminDTO implements Serializable {
	private String id;
	private String password;
	
	public AdminDTO() {
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
