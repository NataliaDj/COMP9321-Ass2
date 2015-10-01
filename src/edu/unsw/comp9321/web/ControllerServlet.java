package edu.unsw.comp9321.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControlServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> commands;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerServlet() {
		super();
		commands = new HashMap<String, Object>();
		commands.put("register", new RegisterCommand());
		commands.put("PAGE_NOT_FOUND", new ErrorCommand());
		commands.put("adminLogin", new AdminLoginCommand());
		commands.put("searchUsers", new SearchUsersCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("banUser", new BanUserCommand());
		commands.put("unbanUser", new UnbanUserCommand());
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Command cmd = resolveCommand(request);
		String next = cmd.execute(request, response);
		//if (next.indexOf('.') < 0) {
		//	cmd = (Command) commands.get(next);
		//	next = cmd.execute(request, response);
		//}
		
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(next);
		dispatcher.forward(request, response);
	}
	
	private Command resolveCommand(HttpServletRequest request) {
		Command cmd = (Command) commands.get(request.getParameter("operation"));
		if (cmd == null) {
			cmd = (Command) commands.get("PAGE_NOT_FOUND");
		}
		return cmd;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
