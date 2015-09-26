package edu.unsw.comp9321.jdbc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * This class connects the frontend (e.g. registerCommand) to the database
 * 
 * So for example. RegistrationCommand calls
 */

import edu.unsw.comp9321.exception.*;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class UserService {
	private BookStoreDAO bookstoreDAO;

	public UserService() {
		 DAOFactory factory = DAOFactory.getInstance();
		 bookstoreDAO = factory.getBookStoreDAO();
	}

	/**
	 * @see com.enterprise.business.PhonebookService#login(java.lang.String, java.lang.String)
	 */
	public UserDTO login(String username, String password)
		throws UserLoginFailedException {

		UserDTO user = null;
		
          //TODO: this should try to find a UserBean using the UserDAO  
          //TODO: throw LoginFailedException if the user is not found or the operation fails.
          //TODO: if the user is found, return the user
        return user;
	}

	public UserDTO addUser(UserDTO user){
		bookstoreDAO.addUser(user);
		try {
			SendConfirmationEmail();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void SendConfirmationEmail() throws IOException
	{
		sendMail("me@something.com", "vincentrubingh@gmail.com", "confirmation", "hi there!", null);
	}
	
	public static void sendMail(String from, String to, String subject, String body, String[] headers) throws IOException {
		System.setProperty("mail.smtp.host", "Vincents-MacBook-Pro.local");
	   
		String[] hosts = new String[] { "Vincents-MacBook-Pro.local", "Vincents-MacBook-Pro", "local", "localhost" };
	    String host = "";
	    String[] mail = new String[] { "mail.smtp.host", "mail.host" };
	    String host_property = "";
	    
	    for (int k = 0; k < 2; k++ ) {
	    	host_property = mail[k];
		for (int i = 0; i < 4; i++) {
			host = hosts[i];
			System.setProperty(host_property, host);
			try {
				URL u = new URL("mailto:"+to);
				MailToURLConnection con = (MailToURLConnection)u.openConnection();
				OutputStream os = con.getOutputStream();
				OutputStreamWriter w = new OutputStreamWriter(os);
			   
				DateFormat df = new SimpleDateFormat("E, d MMM yyyy H:mm:ss Z");
				Date d = new Date();
				String dt = df.format(d);
			    String mid = d.getTime()+from.substring(from.indexOf('@'));

			   w.append("Subject: "+subject+"\r\n");
			   w.append("Date: " +dt+ "\r\n");
			   w.append("Message-ID: <"+mid+ ">\r\n");
			   w.append("From: "+from+"\r\n");
			   w.append("To: <"+to+">\r\n");
			   if(headers!=null) {
			      for(String h: headers)
			         w.append(h).append("\r\n");
			   }
			   w.append("\r\n");

			   w.append(body.replace("\n", "\r\n"));
			   w.flush();
			   w.close();
			   os.close();
			   con.close();
		   } catch (java.net.UnknownHostException e) {
			   System.out.println("host failed: ");
			   System.out.println("name = " + host_property + " and host = " + host);
		   }
		   
	   }
	    }

	   
	}

}
