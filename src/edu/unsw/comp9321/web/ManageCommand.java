package edu.unsw.comp9321.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.BookStoreDAO;

public class ManageCommand implements Command{
	BookStoreDAO bookstoreDAO;
	
	public ManageCommand() {
		bookstoreDAO = new BookStoreDAO();
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getParameter("action") != null) {
			String[] pauseIDs = request.getParameterValues("toPause");
			for (String id: pauseIDs) {
				bookstoreDAO.updatePause(id);
			}
		}
		
		String seller_id = (String) request.getSession().getAttribute("username");
		request.setAttribute("listings", bookstoreDAO.findListing(seller_id)); 
	
		return "/manage.jsp";
	}
}