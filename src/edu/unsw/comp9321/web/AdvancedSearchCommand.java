package edu.unsw.comp9321.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.unsw.comp9321.jdbc.*;

public class AdvancedSearchCommand implements Command {

	private BookStoreDAO bookStoreDAO;

	public AdvancedSearchCommand() {
		DAOFactory factory = DAOFactory.getInstance();
		bookStoreDAO = factory.getBookStoreDAO();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// DEFAULT SEARCH
		// no inputs specified
		String title = null;

		if ((request.getParameter("type") == null)
				&& (request.getParameter("title") == null)
				&& (request.getParameter("author") == null)
				&& (request.getParameter("year") == null)
				&& (request.getParameter("isbn") == null)
				&& (request.getParameter("minPrice") == null)
				&& (request.getParameter("maxPrice") == null)) {
			if (request.getParameter("link") != null
					&& request.getParameter("link").equals("true")) {
				request.setAttribute("publication", bookStoreDAO
						.searchPublications(title).get(0));
				return "/info.jsp";
			} else {
				ArrayList<PublicationDTO> publications = new ArrayList<PublicationDTO>();
				ArrayList<PublicationDTO> random = new ArrayList<PublicationDTO>();
				publications.addAll(bookStoreDAO.searchPublications(null));
				request.setAttribute("publications",
						publications);
				Random rand = new Random();
		        int randomNum;
		        for(int i = 0; i < 10; ++i) {  	
		        	randomNum = rand.nextInt(publications.size());
		        	while(random.contains(publications.get(randomNum))) {
		        		randomNum = rand.nextInt(publications.size());
		        	}
		        	random.add(publications.get(randomNum));
		        }
				request.getSession().setAttribute("random", random);
				request.getSession().setAttribute("found", publications);
				return "/results.jsp";
			}
		}

		// ELSE
		ArrayList<PublicationDTO> publications = new ArrayList<PublicationDTO>();
		ArrayList<PublicationDTO> random = new ArrayList<PublicationDTO>();
		String type = request.getParameter("type");
		title = request.getParameter("title");
		String author = request.getParameter("author");
		int year = -1;
		String isbn = request.getParameter("isbn");
		int minPrice = -1, maxPrice = -1;
		publications.addAll(bookStoreDAO.searchPublications(null));
		Random rand = new Random();
        int randomNum;
        for(int i = 0; i < 10; ++i) {  	
        	randomNum = rand.nextInt(publications.size());
        	while(random.contains(publications.get(randomNum))) {
        		randomNum = rand.nextInt(publications.size());
        	}
        	random.add(publications.get(randomNum));
        }
		request.getSession().setAttribute("random", random);
		if (type != null) {
			if (!type.equals("")) {
				specificSearch(publications, "type", type, year, minPrice,
						maxPrice, false, false, false);
			}
		}
		if (title != null) {
			if (!title.equals("")) {
				specificSearch(publications, "title", title, year, minPrice,
						maxPrice, false, false, false);
			}
		}
		if (author != null) {
			if (!author.equals("")) {
				specificSearch(publications, "author", author, year, minPrice,
						maxPrice, false, false, false);
			}
		}
		if (isbn != null) {
			if (!isbn.equals("")) {
				specificSearch(publications, "isbn", isbn, year, minPrice,
						maxPrice, false, false, false);
			}
		}
		if (request.getParameter("minPrice") != null) {
			if (!request.getParameter("minPrice").equals("")) {
				minPrice = Integer.parseInt(request.getParameter("minPrice"));
				specificSearch(publications, null, null, year, minPrice, maxPrice,
						true, false, false);
			}
		}
		if (request.getParameter("maxPrice") != null) {
			if (!request.getParameter("maxPrice").equals("")) {
				maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
				specificSearch(publications, null, null, year, minPrice, maxPrice,
						false, true, false);
			}
		}
		if (request.getParameter("year") != null) {
			if (!request.getParameter("year").equals("")) {
				year = Integer.parseInt(request.getParameter("year"));
				specificSearch(publications, null, null, year, minPrice, maxPrice,
						false, false, true);
			}
		}
		if (request.getParameter("link") != null
				&& request.getParameter("link").equals("true")) {
			request.setAttribute("publication", publications.get(0));
			return "/info.jsp";
		} else {
			request.setAttribute("publications", publications);
			request.getSession().setAttribute("found", publications);
			return "/results.jsp";
		}
	}

	private void specificSearch(ArrayList<PublicationDTO> publications,
			String searchPref, String input, int year, int minPrice,
			int maxPrice, boolean minPriceSet, boolean maxPriceSet,
			boolean yearSet) {
		ArrayList<PublicationDTO> temp = new ArrayList<PublicationDTO>();
		for (PublicationDTO p : publications) {
			if (searchPref != null) {
				Pattern pattern = Pattern.compile(input,
						Pattern.CASE_INSENSITIVE);
				if (searchPref.equals("type")) {
					if (p.getPubType() != null) {
						Matcher matcher = pattern.matcher(p.getPubType());
						if (matcher.find()) {
							temp.add(p);
						}
					}
				} else if (searchPref.equals("title")) {
					if (p.getTitle() != null) {
						Matcher matcher = pattern.matcher(p.getTitle());
						if (matcher.find()) {
							temp.add(p);
						}
					}
				} else if (searchPref.equals("author")) {
					if (p.getAuthor() != null) {
						Matcher matcher = pattern.matcher(p.getAuthor());
						if (matcher.find()) {
							temp.add(p);
						}
					}
				} else if (searchPref.equals("isbn")) {
					if (p.getIsbn() != null) {
						Matcher matcher = pattern.matcher(p.getIsbn());
						if (matcher.find()) {
							temp.add(p);
						}
					}
				}
			} else {
				if (yearSet && year == p.getPubYear()) {
					temp.add(p);
				} else if (minPriceSet) {
					if (p.getPrice() >= minPrice) {
						temp.add(p);
					}
				} else if (maxPriceSet) {
					if (p.getPrice() <= maxPrice) {
						temp.add(p);
					}
				} else {
					break;
				}
			}
		}
		publications.clear();
		publications.addAll(temp);
	}
}
