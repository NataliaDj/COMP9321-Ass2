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

	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String action = "";
		// if no action, it means first access to register page 
		if (request.getParameter("action") == null) {
			return "./sell.jsp";
		}

		try {
			response.setContentType("text/html");// from response, set content type
			PublicationDTO book = CreatePublication(request, response);
			DAOFactory factory = DAOFactory.getInstance();
			BookStoreDAO bookstoreDAO = factory.getBookStoreDAO();
			bookstoreDAO.newBookListing(book);
			return "./sellSuccess.jsp";
		} catch (Exception e) {
			return "./sellFailed.jsp";
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
		if(request.getParameter("bookYear") != null) {
			book.setPubYear(Integer.parseInt(request.getParameter("bookYear")));
		}
		book.setIsbn(request.getParameter("bookISBN"));
		book.setPicture(request.getParameter("bookPicture"));
		book.setSeller(request.getParameter("bookSeller"));
		if (request.getParameter("bookPause").equals("No")) {
			book.setPause(false);
		} else {
			book.setPause(true);
		}
		
		//print out the book was successfully added
		PrintWriter out = response.getWriter();// from response, get output writer
		out.println("<br/><h3>New book listing</h3><br/>"); 
		out.println("<h4>Book's title = " + book.getTitle() + "</h4><br/>");
		out.println("<h4>Book's author = " + book.getAuthor() + "</h4><br/>"); 
		out.println("<h4>Book's author = " + book.getAuthor() + "</h4><br/>"); 
		
		return book;
		
		
	}
	
}
