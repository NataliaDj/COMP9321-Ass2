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


public class LogoutCommand implements Command{
	
	public LogoutCommand() {
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		String nextPage = "login.jsp";
		RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
		rd.forward(request, response); 

		return null;
	}
}
