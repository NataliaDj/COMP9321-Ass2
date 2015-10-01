package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.unsw.comp9321.jdbc.BookStoreDAO;
import edu.unsw.comp9321.jdbc.DAOFactory;
import edu.unsw.comp9321.jdbc.UserDTO;
import edu.unsw.comp9321.jdbc.UserService;
import edu.unsw.comp9321.jdbc.Utilities;

public class SearchCommand implements Command {
	
	private BookStoreDAO bookStoreDAO;
	
	public SearchCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookStoreDAO = factory.getBookStoreDAO();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String title = "";
		if (request.getParameter("title") != null) {
			title = request.getParameter("title");
		}
		
		if (request.getParameter("link") != null && request.getParameter("link").equals("true")) {
			request.setAttribute("publication", bookStoreDAO.searchPublications(title).get(0));
			return "/info.jsp";
		} else {
			request.setAttribute("publications", bookStoreDAO.searchPublications(title)); 
			return "/results.jsp";
		}
	}
}
