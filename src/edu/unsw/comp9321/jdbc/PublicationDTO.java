package edu.unsw.comp9321.jdbc;

public class PublicationDTO {
	
	public PublicationDTO() {
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPubType() {
		return pubType;
	}
	
	public void setPubType(String pubType) {
		this.pubType = pubType;
	}
	
	public int getPubYear() {
		return pubYear;
	}
	
	public void setPubYear(int pubYear) {
		this.pubYear = pubYear;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public boolean isPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	private String title;
	private int price;
	private String author;
	private String pubType;
	private int pubYear;
	private String isbn;
	private String picture;
	private boolean pause;
	private String seller;
}
