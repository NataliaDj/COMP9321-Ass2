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

		if (request.getParameter("action") == "remove") {
			String[] removeIDs = request.getParameterValues("toRemove");
			for (String id: removeIDs) {
				bookstoreDAO.removeFromCart(id);
			}
		} else if (request.getParameter("action") == "add") {
			String addID = request.getParameter("id");
			String seller = request.getParameter("seller");
			bookstoreDAO.addToCart(addID, seller);
			request.setAttribute("message", "Item was succesffully added to cart");
			return "/info.jsp";
		}
		
		String user = (String) request.getSession().getAttribute("username");
		request.setAttribute("listings", bookstoreDAO.getCartItems(user)); 
	
		return "/cart.jsp";
	}

}
