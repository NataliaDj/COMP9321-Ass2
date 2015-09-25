package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterCommand implements Command{
	
	public RegisterCommand() {
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String action = "";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		 
		if (action.equals("registering")) { //success
			response.setContentType("text/html");// from response, set content type
			PrintWriter out = response.getWriter();// from response, get output writer
			out.println("<b>Submitting!</b>"); 
			 
			//TEMP DISABLED//UserBean userbean = CreateUser(request, response);
			//TEMP DISABLED//UserDelegate user_del = DelegateFactory.getInstance().getUserDelegate();
			//TEMP DISABLED//user_del.addUser(userbean);
			 
		} else {
			String nextPage = "register.jsp";
			RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
			rd.forward(request, response); 
		}
		
		
		
		
		//response.setContentType("text/html");// from response, set content type
		//PrintWriter out = response.getWriter();// from response, get output writer
		//out.println("<b>REGISTERRR</b>");
		return null;
	}
	
	/*
	private UserBean CreateUser(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		UserBean userbean = new UserBean();
		userbean.setFirstname(request.getParameter("firstname"));
		userbean.setLastname(request.getParameter("lastname"));
		userbean.setUsername(request.getParameter("username"));
		userbean.setPassword(request.getParameter("password"));
		
		userbean.setEmail(request.getParameter("email"));
		userbean.setBirthYear(Integer.parseInt(request.getParameter("birth_year")));
		
		userbean.setAddressOne(request.getParameter("address_one"));
		userbean.setAddressTwo(request.getParameter("address_two"));
		userbean.setCity(request.getParameter("city"));
		userbean.setPostalCode(request.getParameter("postal_code"));
		userbean.setState(request.getParameter("state"));
		userbean.setCountry(request.getParameter("country"));
		
		PrintWriter out = response.getWriter();// from response, get output writer
		out.println("<br>"); 
		out.println("User's name = " + userbean.getFirstname() + " " + userbean.getLastname()); 
		
		return userbean;
		
		
	}*/
}
