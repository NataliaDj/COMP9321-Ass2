package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.unsw.comp9321.jdbc.UserDTO;
import edu.unsw.comp9321.jdbc.UserService;
import edu.unsw.comp9321.jdbc.Utilities;

public class SearchCommand implements Command {
	
	public SearchCommand() {
		
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String title = "";
		if (request.getParameter("title") != null) {
			title = request.getParameter("title");
		}
		
		UserService service = new UserService();
		request.setAttribute("publications", service.searchPublications(title)); 
	
		return "/results.jsp";
	}
}
