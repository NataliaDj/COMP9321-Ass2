package edu.unsw.comp9321.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.BookStoreDAO;

public class CartCommand implements Command {
	BookStoreDAO bookstoreDAO;
	
	public CartCommand() {
		bookstoreDAO = new BookStoreDAO();
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("action") != null) {
			String[] removeIDs = request.getParameterValues("toRemove");
			for (String id: removeIDs) {
				bookstoreDAO.removeFromCart(id);
			}
		}
		
		String user = (String) request.getSession().getAttribute("username");
		request.setAttribute("listings", bookstoreDAO.getCartItems(user)); 
	
		return "/cart.jsp";
	}

}