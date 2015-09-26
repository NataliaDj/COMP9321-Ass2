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


public class RegisterCommand implements Command{
	
	public RegisterCommand() {
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//InetAddress ip = InetAddress.getLocalHost();
		//System.out.println("hostaddress = " + ip.getHostName());
		
		String action = "";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		 
		if (action.equals("registering")) { //success
			register(request, response);
			 
		} else if (action.equals("activation")) {
			response.setContentType("text/html");// from response, set content type
			PrintWriter out = response.getWriter();// from response, get output writer
			out.println("<b>Activating " + request.getParameter("username") + "!</b>"); 
			
			UserService service = new UserService();
			service.activateUser(request.getParameter("username")); // NEED ERROR HANDLING
			
		} else {
			String nextPage = "register.jsp";
			RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
			rd.forward(request, response); 
		}

		return null;
	}
	
	/**
	 * Attempt to register the user with information contained in request
	 * from register.jsp
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");// from response, set content type
		PrintWriter out = response.getWriter();// from response, get output writer
		out.println("<b>Submitting!</b>"); 
		 
		UserDTO user = CreateUser(request, response);
				
		UserService service = new UserService();
		service.addUser(user);
		//TEMP DISABLED//UserDelegate user_del = DelegateFactory.getInstance().getUserDelegate();
		//TEMP DISABLED//user_del.addUser(userbean);
	}
	
	private UserDTO CreateUser(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		UserDTO user = new UserDTO();
		user.setFirstName(request.getParameter("firstname"));
		user.setLastName(request.getParameter("lastname"));
		user.setUsername(request.getParameter("username"));
		String password = request.getParameter("password");
		user.setPassword(Utilities.generateMD5(password)); // don't forget to hash password
		
		
		user.setEmail(request.getParameter("email"));
		user.setBirthYear(Integer.parseInt(request.getParameter("birth_year")));
		
		//user.setAddressOne(request.getParameter("address_one"));
		//user.setAddressTwo(request.getParameter("address_two"));
		//user.setCity(request.getParameter("city"));
		//user.setPostalCode(request.getParameter("postal_code"));
		//user.setState(request.getParameter("state"));
		//user.setCountry(request.getParameter("country"));
		
		PrintWriter out = response.getWriter();// from response, get output writer
		out.println("<br>"); 
		out.println("User's name = " + user.getFirstName() + " " + user.getLastName()); 
		
		return user;
		
		
	}
}
