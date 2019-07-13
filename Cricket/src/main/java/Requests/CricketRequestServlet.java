package Requests;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		c.sendGet();
	}

	private void sendGet() throws Exception {
		String apikey = "WmPJrX2s3KMyZVPFwlm1vxXLXKw1";
		String id = "1136617";
		String url = "http://cricapi.com/api/cricketScore?apikey="+apikey+"&unique_id="+id;

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);


		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + 
				response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		ResponsePojo responsePojo = mapper.readValue(result.toString(), ResponsePojo.class);
		findWinningTeam(responsePojo);
		getRoundRotation(responsePojo);
		System.out.println(responsePojo.getScore());
	}

	private void getRoundRotation(ResponsePojo responsePojo) {
		String a = "140/8";
		a = a.charAt(a.length() - 1) + a.substring(0, a.length() - 1);
		System.out.println(a);
		
	}

	private void findWinningTeam(ResponsePojo responsePojo) {
		String pattern = "v";

	      // Create a Pattern object
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(responsePojo.getScore());
	      if (m.find( )) {
	    	  System.out.println(m.group(0));
	      }else {
	         
	      }
		String a = "140/8";
	}
}
