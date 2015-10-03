package edu.unsw.comp9321.web;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.*;

public class ViewPublicationCommand implements Command {
	BookStoreDAO bookstoreDAO;
	
	public ViewPublicationCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookstoreDAO = factory.getBookStoreDAO();
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "/";
		int id = Integer.parseInt((String) request.getParameter("id"));
		String query = "select * from publications where id = " + id;
		ResultSet rs = bookstoreDAO.queryDatabase(query);
		try {
			if (rs.next()) {
				PublicationDTO pub = bookstoreDAO.createPublication(rs);
				request.setAttribute("publication", pub);
				nextPage = "/info.jsp";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextPage;
	}

}
