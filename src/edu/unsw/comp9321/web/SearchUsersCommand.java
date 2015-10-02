package edu.unsw.comp9321.web;

import java.io.IOException;

import edu.unsw.comp9321.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Session;

import edu.unsw.comp9321.jdbc.*;

public class SearchUsersCommand implements Command {
	BookStoreDAO bookstoreDAO;
	
	SearchUsersCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookstoreDAO = factory.getBookStoreDAO();
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "/register.jsp";
		String isAdmin = (String) request.getSession().getAttribute("adminAccount");
		if (isAdmin != null && isAdmin.equals("yes")) {
			nextPage = "/WEB-INF/adminDir/searchUsers.jsp";
			String username = request.getParameter("username");
			UserDTO userDTO = bookstoreDAO.getUserDTO(username);
			if (userDTO != null) {
				request.getSession().setAttribute("userDTO", userDTO);
				nextPage = "/WEB-INF/adminDir/viewUser.jsp";
			}
		}
		return nextPage;
	}

}
