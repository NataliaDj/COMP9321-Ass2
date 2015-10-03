package edu.unsw.comp9321.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.*;

public class ViewHistoryCommand implements Command {
	BookStoreDAO bookstoreDAO;
	
	public ViewHistoryCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookstoreDAO = factory.getBookStoreDAO();
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "/";
		String adminAccount = (String) request.getSession().getAttribute("adminAccount");
		UserDTO userDTO = (UserDTO) request.getSession().getAttribute("userDTO");
		boolean isBuyer = false;
		if (userDTO != null && userDTO.getBuyerDTO() != null) {
			isBuyer = true;
		}
		
		if (adminAccount != null && isBuyer && adminAccount.equals("yes")) {
			List<CartEntryDTO> cart = bookstoreDAO.getCartDTO(userDTO.getUsername());
			request.getSession().setAttribute("cart", cart);
			nextPage = "/WEB-INF/adminDir/viewHistory.jsp";
		}
		return nextPage;
	}

}
