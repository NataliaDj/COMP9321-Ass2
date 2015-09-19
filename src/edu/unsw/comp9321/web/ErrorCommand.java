package edu.unsw.comp9321.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand {
	
	public ErrorCommand() {
		
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/error.jsp";
	}
}
