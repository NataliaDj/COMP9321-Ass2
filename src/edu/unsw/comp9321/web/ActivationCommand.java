package edu.unsw.comp9321.web;
/**
 * When a user clicks the link in their email to confirm their account,
 * it will take them to this page
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import edu.unsw.comp9321.jdbc.BuyerDTO;
import edu.unsw.comp9321.jdbc.SellerDTO;
import edu.unsw.comp9321.jdbc.UserDTO;
import edu.unsw.comp9321.jdbc.UserService;
import edu.unsw.comp9321.jdbc.Utilities;


public class ActivationCommand implements Command{
	
	public ActivationCommand() {
		
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		//PrintWriter out = response.getWriter();
		//response.setContentType("text/html");
			
		//out.println("<b>Activating " + request.getParameter("username") + "!</b>"); 
			
		UserService service = new UserService();
		service.activateUser(request.getParameter("username")); // NEED ERROR HANDLING
		
		request.setAttribute("error", "Your account has been successfully activated!");
		String nextPage = "login.jsp";
		
		RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
		rd.forward(request, response);
		
		return null;
	}
	
	
}
