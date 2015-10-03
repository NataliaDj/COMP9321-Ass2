package edu.unsw.comp9321.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.BookStoreDAO;
import edu.unsw.comp9321.jdbc.DAOFactory;
import edu.unsw.comp9321.jdbc.PublicationDTO;

public class BackPageCommand implements Command {
	
	private BookStoreDAO bookStoreDAO;
	
	public BackPageCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookStoreDAO = factory.getBookStoreDAO();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int currPage = Integer.parseInt(request.getParameter("page"));
		ArrayList<PublicationDTO> result = (ArrayList<PublicationDTO>) request.getSession().getAttribute("totalPublications");
		int totalPages = (Integer) request.getSession().getAttribute("totalPages");
		ArrayList<PublicationDTO> publications = new ArrayList<PublicationDTO>();
		
		if ((currPage - 1) <= totalPages+1 && currPage > 0) {
			for (int i = (currPage - 1) * 10; i < ((currPage - 1) * 10 + 10); ++i) {
	    		if (i == result.size()){ 
	    			break;
	    		}
	    		publications.add(result.get(i));
			}
		}
		request.getSession().setAttribute("publications", publications);
		request.getSession().setAttribute("currPage", currPage);
		return "/results.jsp";
		
	}
}