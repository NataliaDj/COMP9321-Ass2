package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.UserDTO;
import edu.unsw.comp9321.jdbc.UserService;
import edu.unsw.comp9321.jdbc.Utilities;


public class LoginCommand implements Command{
	
	public LoginCommand() {
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String action = "";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		 
		if (action.equals("loggingin")) {
			response.setContentType("text/html");// from response, set content type
			PrintWriter out = response.getWriter();// from response, get output writer
			
			; 
			UserService service = new UserService();
			
			String password = request.getParameter("password");
			
			UserDTO user = service.login(request.getParameter("username"), Utilities.generateMD5(password));
			if (user == null) {
				out.println("<b>Could not log in!</b>");
			} else {
				// found user
				out.println("<b>Successfully logged in!!</b>");
			}
		} else {
			// if no action, just display login jsp page
			String nextPage = "login.jsp";
			RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
			rd.forward(request, response); 
		}

		return null;
	}
	

}
