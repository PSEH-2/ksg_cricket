package Requests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Handler.RequestHandler;
import pojos.ResponsePojo;

public class RequestTest {
	
	ResponsePojo responsePojo = new ResponsePojo();
	@Before
	public void setUp() {
		responsePojo.setScore("Sunrisers Hyderabad 139/7  v Chennai Super Kings 140/8 *");
		responsePojo.setTeam1("Sunrisers Hyderabad");
		responsePojo.setTeam2("Chennai Super Kings");
	}
	
	@Test
	public void Test1() {
		RequestHandler handler = new RequestHandler();
		handler.checkIfTeamIsWinner(responsePojo, 1);
		
	}
	
	@Test
	public void Test2() {
		RequestHandler handler = new RequestHandler();
		handler.getWinningTeamScore(responsePojo);
		
	}
	
	@Test
	public void Test3() {
		RequestHandler handler = new RequestHandler();
		String result = handler.getRoundRotation("140/8");
		String expected = "8140/";
		Assert.assertEquals(expected,result);
		
	}

}
