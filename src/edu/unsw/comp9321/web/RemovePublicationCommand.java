package edu.unsw.comp9321.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.BookStoreDAO;
import edu.unsw.comp9321.jdbc.DAOFactory;

public class RemovePublicationCommand implements Command{
	BookStoreDAO bookstoreDAO;

	public RemovePublicationCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookstoreDAO = factory.getBookStoreDAO();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String adminAccount = (String) request.getSession().getAttribute("adminAccount");
		String temp = request.getParameter("id");
		if (temp != "" && adminAccount != null && adminAccount.equals("yes")) {
			int id = Integer.parseInt(temp);
			bookstoreDAO.removePublication(id);
		}
		return "/";
	}
}
