package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.unsw.comp9321.jdbc.BookStoreDAO;
import edu.unsw.comp9321.jdbc.DAOFactory;
import edu.unsw.comp9321.jdbc.PublicationDTO;
import edu.unsw.comp9321.jdbc.UserDTO;
import edu.unsw.comp9321.jdbc.UserService;
import edu.unsw.comp9321.jdbc.Utilities;

public class SearchCommand implements Command {
	
	private BookStoreDAO bookStoreDAO;
	
	public SearchCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookStoreDAO = factory.getBookStoreDAO();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String title = "";
		if (request.getParameter("title") != null) {
			title = request.getParameter("title");
		}
		ArrayList<PublicationDTO> result = new ArrayList<PublicationDTO>(bookStoreDAO.searchPublications(title));
		ArrayList<PublicationDTO> random = new ArrayList<PublicationDTO>();
		Random rand = new Random();
        int randomNum;
        for(int i = 0; i < 10; ++i) {  	
        	randomNum = rand.nextInt(result.size());
        	while(random.contains(result.get(randomNum))) {
        		randomNum = rand.nextInt(result.size());
        	}
        	random.add(result.get(randomNum));
        }
		request.getSession().setAttribute("random", random);
		request.getSession().setAttribute("totalPublications", result);
		request.getSession().setAttribute("totalMatches", result.size());
		request.getSession().setAttribute("currPage", 1);
		int totalPages = result.size()/10;
		if (result.size() % 10 == 0) {
			totalPages--;
		}
		request.getSession().setAttribute("totalPages", totalPages);
		ArrayList<PublicationDTO> publications = new ArrayList<PublicationDTO>();
    	for (int i = 0; i < 10; ++i) {
    		if (i == result.size()) {
    			break;
    		}
    		publications.add(result.get(i));
    	}
		request.getSession().setAttribute("publications", publications);
		return "/results.jsp";
	}
}
