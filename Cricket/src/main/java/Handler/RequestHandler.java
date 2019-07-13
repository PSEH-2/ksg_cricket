package Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import pojos.ResponsePojo;

public class RequestHandler {
	
	ObjectMapper mapper = new ObjectMapper();
	
	public String sendGet(String matchId) throws Exception {
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
		StringBuilder builder = new StringBuilder();
		builder.append("Team - 1 : " + responsePojo.getTeam1() + checkIfTeamIsWinner(responsePojo, 1) + "\n");
		builder.append("Team - 2 : " + responsePojo.getTeam2() + checkIfTeamIsWinner(responsePojo, 2) + "\n");
		builder.append("Winning team’s score : " + getWinningTeamScore(responsePojo) + "\n");
		builder.append("Round rotation : " + getRoundRotation(getWinningTeamScore(responsePojo)) + "\n");
		System.out.println(builder.toString());
		
		return builder.toString();

	}
	
	public String checkIfTeamIsWinner(ResponsePojo responsePojo, int i) {
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

	public String getWinningTeamScore(ResponsePojo responsePojo) {
		String[] parts = responsePojo.getScore().split("v");
		if(parts[0].contains("*")) {
			String[] parts1 = parts[0].split(responsePojo.getTeam1());
			return parts1[1].substring(1, parts1[1].length() - 2);
		} else {
			String[] parts1 = parts[1].split(responsePojo.getTeam2());
			return parts1[1].substring(1, parts1[1].length() - 2);
		}
	}

	public String getRoundRotation(String score) {
		score = score.charAt(score.length() - 1) + score.substring(0, score.length() - 1);
		return score;
	}
}
