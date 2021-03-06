package edu.unsw.comp9321.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.unsw.comp9321.exception.ServiceLocatorException;
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
			String nextPage = "";
			if (register(request, response)) {
				request.setAttribute("error", "Email sent for activation");
				nextPage = "login.jsp";
			} else {
				request.setAttribute("info", "<p><b>Either the username or email is already taken</b></p>");
				nextPage = "register.jsp";
			}
			RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
			rd.forward(request, response); 
			
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
		
		String current_password = user.getPassword();
		// create user object
		user = CreateUser(request, response, user);
		user = CreateBuyer(request, user, true);
		user = CreateSeller(request, user, true); 
				
		// if password is empty, keep it
		if (request.getParameter("password").isEmpty() || 
				request.getParameter("password").length() == 0) { // if empty
			// make sure to keep old one
			user.setPassword(current_password);
		}
				
		// get service, update in database, and display message
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
	 * @throws ServletException 
	 */
	private boolean register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");// from response, set content type
		PrintWriter out = response.getWriter();// from response, get output writer
		out.println("<b>Submitting!</b>"); 
		 
		UserDTO user = new UserDTO();
		user = CreateUser(request, response, user);
		user = CreateBuyer(request, user);
		user = CreateSeller(request, user);
		
		UserService service = new UserService();
		
		// if adding user is unsuccessful, it's beacause of username or email duplication
		if (!service.addUser(user)) {
			return false;
		} else {
			return true;
		}
		
}	
	private UserDTO CreateBuyer(HttpServletRequest request, UserDTO user)
			throws IOException {
		return CreateBuyer(request, user, false);
	}
	private UserDTO CreateBuyer(HttpServletRequest request, UserDTO user, boolean update) 
			throws IOException {
		if (request.getParameter("buyer") != null || (update && request.getParameter("credit_card") != null)) {
			try {
				if (update) {
					BuyerDTO buyer = new BuyerDTO();
					buyer.setCreditCard(Integer.parseInt(request.getParameter("credit_card")));
					user.setBuyerDTO(buyer);
				} else if (request.getParameter("buyer").equals("on")) {
					BuyerDTO buyer = new BuyerDTO();
					buyer.setCreditCard(Integer.parseInt(request.getParameter("credit_card")));
					user.setBuyerDTO(buyer);
				} 
			} catch (NumberFormatException e) {
			
			}
		}
		return user;
	}
	
	private UserDTO CreateSeller(HttpServletRequest request, UserDTO user) 
			throws IOException {
		return CreateSeller(request, user, false);
	}
	private UserDTO CreateSeller(HttpServletRequest request, UserDTO user, boolean update) 
			throws IOException {
		if (request.getParameter("seller") != null || (update && request.getParameter("paypal") != null)) {
			try {
				if (update) {
					SellerDTO seller = new SellerDTO();
					seller.setPaypal(request.getParameter("paypal"));
					user.setSellerDTO(seller);
				} else if (request.getParameter("seller").equals("on")) {
					SellerDTO seller = new SellerDTO();
					seller.setPaypal(request.getParameter("paypal"));
					user.setSellerDTO(seller);
				} 
			} catch (NumberFormatException e) {
				
			}
		}
		return user;
	}
	
	private UserDTO CreateUser(HttpServletRequest request, HttpServletResponse response, UserDTO user) 
			throws IOException {
				
		user.setNickname(request.getParameter("nickname"));
		user.setFirstName(request.getParameter("firstname"));
		user.setLastName(request.getParameter("lastname"));
		user.setUsername(request.getParameter("username"));
		String password = request.getParameter("password");
		user.setPassword(Utilities.generateMD5(password)); // don't forget to hash password
		
		user.setEmail(request.getParameter("email"));
		user.setBirthYear(Integer.parseInt(request.getParameter("birth_year")));
		
		
		String address = Utilities.VerifyInput(request.getParameter("address_one")) + "," +
						Utilities.VerifyInput(request.getParameter("address_two")) + "," +
						Utilities.VerifyInput(request.getParameter("city")) + "," +
						Utilities.VerifyInput(request.getParameter("postal_code")) + "," +
						Utilities.VerifyInput(request.getParameter("state")) + "," +
						Utilities.VerifyInput(request.getParameter("country"));
		user.setAddress(address);
		
		return user;
		
		
	}
	
	
	
	
}
