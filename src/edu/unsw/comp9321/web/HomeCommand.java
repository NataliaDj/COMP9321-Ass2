package edu.unsw.comp9321.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.*;

public class HomeCommand implements Command {
	
	private BookStoreDAO bookStoreDAO;

	public HomeCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookStoreDAO = factory.getBookStoreDAO();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ArrayList<PublicationDTO> random = new ArrayList<PublicationDTO>();
		ArrayList<PublicationDTO> temp = new ArrayList<PublicationDTO>(bookStoreDAO.searchPublications(null));
		Random rand = new Random();
        int randomNum;
        for(int i = 0; i < 10; ++i) {  	
        	randomNum = rand.nextInt(temp.size());
        	while(random.contains(temp.get(randomNum))) {
        		randomNum = rand.nextInt(temp.size());
        	}
        	random.add(temp.get(randomNum));
        }
		request.setAttribute("random", random);
		return "/index.jsp";
	}
}
