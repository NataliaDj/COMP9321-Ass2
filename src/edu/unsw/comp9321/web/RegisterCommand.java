package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.unsw.comp9321.jdbc.BuyerDTO;
import edu.unsw.comp9321.jdbc.SellerDTO;
import edu.unsw.comp9321.jdbc.UserDTO;
import edu.unsw.comp9321.jdbc.UserService;
import edu.unsw.comp9321.jdbc.Utilities;


public class RegisterCommand implements Command{
	
	public RegisterCommand() {
		
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String action = "";
		UserDTO user = new UserDTO();
		request.setAttribute("user", user);	
		
		// if no action, it means first access to register page 
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		 
		if (action.equals("registering")) { // trying to register with form data
			register(request, response);
			 
		} else if (action.equals("activation")) { // trying to activate through link in email
			response.setContentType("text/html");
			
			out.println("<b>Activating " + request.getParameter("username") + "!</b>"); 
			
			UserService service = new UserService();
			service.activateUser(request.getParameter("username")); // NEED ERROR HANDLING
		} else if (action.equals("updating_user")) { // update user
			updateUser(request, response);
		} else {
			
			// test if logged in or not
			HttpSession session = request.getSession(false);  
	        if (session != null){
	        	if (session.getAttribute("user") != null) {
	        		out.println("<b>You obviously deserve a better page here " + session.getAttribute("username") + "!</b>"); 
	        		
	        		// set attributes
	        		user = (UserDTO) session.getAttribute("user");
	        		request.setAttribute("user", user);
	        	}
	        }
	        
	        String nextPage = "register.jsp";
			RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
			rd.forward(request, response);
	        
		}
		return null;
	}
	
	/**
	 * Get the right type of user (buyer or seller)
	 * 
	 * @param request
	 * @param response
	 * @param user_type
	 * @return
	 * @throws IOException
	 */
	private UserDTO userHelper(HttpServletRequest request, HttpServletResponse response, String user_type) 
			throws IOException {		 
		UserDTO user;
		if (user_type.equalsIgnoreCase("seller")) {
			user = CreateSeller(request, response);
		} else {
			user = CreateBuyer(request, response);
		}
		
		return user;
	}
	
	/**
	 * Update user information in database and session
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		// first current session user
		HttpSession session = request.getSession(false);
		UserDTO user = (UserDTO) session.getAttribute("user");
		
		// if password is empty, keep it
		String current_password = user.getPassword();
		user = CreateUser(request, response, user); 
		if (request.getParameter("password").isEmpty()) { // if empty
			// make sure to keep old one
			user.setPassword(current_password);
		}
		
		// get service and update in database
		UserService service = new UserService();
		if (service.updateUser(user)) { // if successfully updated
			request.setAttribute("info", "<p><b>Your profile has successfully been updated</b></p>");
		} else {
			request.setAttribute("info", "<p><b>Error: profile could not be updated</b></p>");
		}
		
		// finally put user back into session.
		session.setAttribute("user", user);
		request.setAttribute("user", user);
		
		// send to updated page
		String nextPage = "register.jsp";
		
		RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
		rd.forward(request, response);
		
		
		

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
		 
		UserDTO user = userHelper(request, response, request.getParameter("user_type"));
		UserService service = new UserService();
		service.addUser(user);
		
}
	
	private BuyerDTO CreateBuyer(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		BuyerDTO buyer = new BuyerDTO();
		buyer = (BuyerDTO) CreateUser(request, response, buyer);
		buyer.setCreditCard(Integer.parseInt(request.getParameter("payment")));
		return buyer;
	}
	
	private SellerDTO CreateSeller(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		SellerDTO seller = new SellerDTO();
		seller = (SellerDTO) CreateUser(request, response, seller);
		seller.setPaypal(request.getParameter("payment"));
		return seller;
	}
	
	private UserDTO CreateUser(HttpServletRequest request, HttpServletResponse response, UserDTO user) 
			throws IOException {
		
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
		
		return user;
		
		
	}
}
