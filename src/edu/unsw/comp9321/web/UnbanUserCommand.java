package edu.unsw.comp9321.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.*;

public class UnbanUserCommand implements Command {
	BookStoreDAO bookstoreDAO;
	
	public UnbanUserCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookstoreDAO = factory.getBookStoreDAO();
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "/register.jsp";
		String isAdmin = (String) request.getSession().getAttribute("adminAccount");
		if (isAdmin != null && isAdmin.equals("yes")) {
			nextPage = "/WEB-INF/adminDir/viewUser.jsp";
			UserDTO oldDTO = (UserDTO) request.getSession().getAttribute("userDTO");
			bookstoreDAO.updateUserBan(oldDTO, false);
			UserDTO newDTO = bookstoreDAO.getUserDTO(oldDTO.getUsername());
			request.getSession().setAttribute("userDTO", newDTO);
		}
		return nextPage;
	}

}
