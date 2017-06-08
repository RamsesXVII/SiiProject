package validationAggregated;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import twitter.CityUtility;
import validation.User2tweetBuilder;

public class validationAggregatedTest {

	private String pathToTestFile;
	private User2tweetBuilder userTBuilder;
	private User2tweetBuilder userTBuilder2User;


	@Before
	public void setUp() throws Exception {
		
		this.pathToTestFile="resources/test20tweets1user.txt";
		this.userTBuilder= new User2tweetBuilder(pathToTestFile);
		this.userTBuilder2User=new User2tweetBuilder("resources/test20tweets2user.txt");
		

	}

	@Test
	public void tweets20_1User() throws IOException {

		this.userTBuilder.computerUser2tweet();
		assertEquals(1, this.userTBuilder.getUser2tweet().size());
		assertEquals(new Integer(20), this.userTBuilder.getUser2TweetsCount().get("22077441"));

	}
	
	@Test
	public void tweets20_2User() throws IOException {
		
		this.userTBuilder2User.computerUser2tweet();
		assertEquals(2, this.userTBuilder2User.getUser2tweet().size());
		assertEquals(new Integer(20), this.userTBuilder2User.getUser2TweetsCount().get("22077441"));
		assertEquals(new Integer(20), this.userTBuilder2User.getUser2TweetsCount().get("73016661"));


	}
	
	

}