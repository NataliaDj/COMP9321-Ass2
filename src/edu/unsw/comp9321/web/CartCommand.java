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

		String user = (String) request.getSession().getAttribute("username");
		if (request.getParameter("operation") != null && request.getParameter("operation").equals("removeFromCart")) {
			String[] removeIDs = request.getParameterValues("toRemove");
			for (String id: removeIDs) {
				bookstoreDAO.removeFromCart(id, user);
			}
		} else if (request.getParameter("operation") != null && request.getParameter("operation").equals("checkout")) {
			bookstoreDAO.checkout(user);
			request.setAttribute("message", "Items were succesffully bought");
			return "/sellSuccess.jsp";
		} else if (request.getParameter("action") != null && request.getParameter("action").equals("add")) {
			String addID = request.getParameter("id");
			bookstoreDAO.addToCart(addID, user);
			request.setAttribute("message", "Item was succesffully added to cart");
		}
		
		request.setAttribute("listings", bookstoreDAO.getCartItems(user)); 
		System.out.println(user);
	
		return "/cart.jsp";
	}

}
