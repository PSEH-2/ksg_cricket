package Requests;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import pojos.ResponsePojo;

//@WebServlet("/score")
public class CricketRequestServlet extends HttpServlet{

	ObjectMapper mapper = new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().write("Hello World! Maven Web Project Example.");
	}

	public static void main(String args[]) throws Exception {
		CricketRequestServlet c = new CricketRequestServlet();
		Scanner scanner = new Scanner(System.in);
		String matchId = scanner.next();
		scanner.close();
		//c.sendGet("1136617");
		c.sendGet(matchId);
	}

	private void sendGet(String matchId) throws Exception {
		String apikey = "WmPJrX2s3KMyZVPFwlm1vxXLXKw1";
		String id = matchId;
		String url = "http://cricapi.com/api/cricketScore?apikey="+apikey+"&unique_id="+id;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		ResponsePojo responsePojo = mapper.readValue(result.toString(), ResponsePojo.class);
		//Output
		System.out.println("Team - 1 : " + responsePojo.getTeam1() + checkIfTeamIsWinner(responsePojo, 1));
		System.out.println("Team - 2 : " + responsePojo.getTeam2() + checkIfTeamIsWinner(responsePojo, 2));
		System.out.println("Winning team’s score : " + getWinningTeamScore(responsePojo));
		System.out.println("Round rotation : " + getRoundRotation(getWinningTeamScore(responsePojo)));

	}
	
	private String checkIfTeamIsWinner(ResponsePojo responsePojo, int i) {
		String[] parts = responsePojo.getScore().split("v");
		if(parts[0].contains("*")) {
			if(i == 1) {
				return " (winner)";
			} else {
				return "";
			}
		} else {
			if(i == 2) {
				return " (winner)";
			} else {
				return "";
			}
		}
	}

	private String getWinningTeamScore(ResponsePojo responsePojo) {
		String[] parts = responsePojo.getScore().split("v");
		if(parts[0].contains("*")) {
			String[] parts1 = parts[0].split(responsePojo.getTeam1());
			return parts1[1].substring(0, parts1[1].length() - 2);
		} else {
			String[] parts1 = parts[1].split(responsePojo.getTeam2());
			return parts1[1].substring(0, parts1[1].length() - 2);
		}
	}

	private String getRoundRotation(String score) {
		score = score.charAt(score.length() - 1) + score.substring(0, score.length() - 1);
		return score;
	}
}
