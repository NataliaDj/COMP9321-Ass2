package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {
	
	public ErrorCommand() {
		
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");// from response, set content type
		PrintWriter out = response.getWriter();// from response, get output writer
		out.println("<b>	There is no such an operator. Please try again!</b>");
	    return null;
	    
	}
}
