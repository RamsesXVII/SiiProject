package utilitiesTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.SQLException;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ValidationTest {

	private static CityOfTweetsExpertTest teller;
	private static String tweetFromMilan="oi mona non fa il bischero";
	private static String tweetFromRome="ao esiste un solo un capitano e se chiama Totti";

	@BeforeClass
	public static void setUp() throws Exception {
		MySQLAccessTest msql = new MySQLAccessTest();
		msql= new MySQLAccessTest();
		teller= new  CityOfTweetsExpertTest();
		BufferedReader br = new BufferedReader(new FileReader("test/mysqlTestGen.txt"));
		String line;
		while ((line = br.readLine()) != null) {
			if(line.replaceAll("\\s+", "").length()>0)
				msql.insertNewRecord(line);
		}
		br.close();
		
	}

	@Test
	public void getCandidateCitiesTest() throws ClassNotFoundException, SQLException {
		teller.guessThePossibleCities(tweetFromMilan.split(" "));
		String result = teller.getCandidateCities().stream().findFirst().get().getCity().toString();
		assertEquals("45.4627124$9.1076927", result);
	}

	@Test
	public void getCandidateCities2Test() throws ClassNotFoundException, SQLException {
		teller.guessThePossibleCities(tweetFromRome.split(" "));
		String result = teller.getCandidateCities().stream().findFirst().get().getCity().toString();
		assertEquals("41.909986$12.39591493", result);
	}


	@AfterClass
	public static void cleanup() throws SQLException, ClassNotFoundException{
		MySQLAccessTest msql2 = new MySQLAccessTest();
		msql2.insertNewRecord("drop table testTable");

	}
}





