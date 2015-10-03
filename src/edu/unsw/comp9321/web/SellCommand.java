package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.BookStoreDAO;
import edu.unsw.comp9321.jdbc.DAOFactory;
import edu.unsw.comp9321.jdbc.PublicationDTO;

public class SellCommand implements Command {

	private BookStoreDAO bookstoreDAO;
	
	SellCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookstoreDAO = factory.getBookStoreDAO();
	}

	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// if no action, it means first access to register page 
		if (request.getParameter("action") == null) {
			return "/sell.jsp";
		}

		try {
			PublicationDTO book = CreatePublication(request, response);
			bookstoreDAO.newBookListing(book);
			return "/sellSuccess.jsp";
		} catch (Exception e) {
			return "/sellFailed.jsp";
		}
		
	}
	
	private PublicationDTO CreatePublication(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		//create a new Publication
		PublicationDTO book = new PublicationDTO();
		
		//fill in the DTO with its values from sell.jsp
		book.setTitle(request.getParameter("bookTitle"));
		book.setAuthor(request.getParameter("bookAuthor"));
		if(request.getParameter("bookPrice") != null) {
			book.setPrice(Integer.parseInt(request.getParameter("bookPrice")));
		}
		book.setPubType(request.getParameter("bookType"));
		if(request.getParameter("bookYear") != null && request.getParameter("bookYear") != "") {
			book.setPubYear(Integer.parseInt(request.getParameter("bookYear")));
		}
		book.setIsbn(request.getParameter("bookISBN"));
		book.setPicture(request.getParameter("bookPicture"));
		if(request.getParameter("bookSeller") != null && request.getParameter("bookSeller") != "") {
			book.setSeller(request.getParameter("bookSeller"));
		}
		book.setSeller(request.getParameter("bookSeller"));
		if (request.getParameter("bookPause").equals("No")) {
			book.setPause(false);
		} else {
			book.setPause(true);
		}
		
		return book;
		
		
	}
	
}
