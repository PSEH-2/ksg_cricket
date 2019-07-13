package Requests;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Handler.RequestHandler;

//@WebServlet("/score")
public class CricketRequestServlet extends HttpServlet{

	static RequestHandler handler = new RequestHandler();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String matchId = req.getParameter("Match-UniqueID");
		try {
			resp.getWriter().write(handler.sendGet(matchId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setContentType("text/plain");
		resp.getWriter().write("Hello World! Maven Web Project Example.");
	}

	public static void main(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String matchId = scanner.next();
		scanner.close();
		handler.sendGet(matchId);
	}

	
}
