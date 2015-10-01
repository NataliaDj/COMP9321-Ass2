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
		String nextPage = "results.jsp";
		request.setAttribute("publications", "publications"); 
		
		if (action.equals("loggingin")) {
			response.setContentType("text/html");// from response, set content type
			PrintWriter out = response.getWriter();// from response, get output writer
			
			; 
			UserService service = new UserService();
			
			String password = request.getParameter("password");
			
			UserDTO user = service.login(request.getParameter("username"), Utilities.generateMD5(password));
			if (user == null) {
				request.setAttribute("error", "Username and/or password is incorrect, please try again." );
				
			} else {
				// found user
				HttpSession session = request.getSession(true);
				session.setAttribute("username", request.getParameter("username")); // set user session so it remembers logged in
				session.setAttribute("user", user);
				nextPage = "register.jsp";
			}
		} 
		
		UserService service = new UserService();
		request.setAttribute("publications", service.searchPublications(title)); 
		
		RequestDispatcher rd = request.getRequestDispatcher("/results.jsp");
		rd.forward(request, response); 
	
		return null;
	}*/
}
