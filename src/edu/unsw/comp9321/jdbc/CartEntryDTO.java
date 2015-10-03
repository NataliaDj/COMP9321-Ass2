package edu.unsw.comp9321.jdbc;

import java.util.Date;

public class CartEntryDTO {
	private String username;
	private Date added;
	private Date removed;
	private Date purchased;
	private PublicationDTO publication;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getAdded() {
		return added;
	}
	public void setAdded(Date added) {
		this.added = added;
	}
	public Date getRemoved() {
		return removed;
	}
	public void setRemoved(Date removed) {
		this.removed = removed;
	}
	public Date getPurchased() {
		return purchased;
	}
	public void setPurchased(Date purchased) {
		this.purchased = purchased;
	}
	public PublicationDTO getPublication() {
		return publication;
	}
	public void setPublication(PublicationDTO publication) {
		this.publication = publication;
	}
	
}
