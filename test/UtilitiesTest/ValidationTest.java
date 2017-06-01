package UtilitiesTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import validation.City2Score;

public class ValidationTest {

	private static CityOfTweetsTellerTest teller;
	private static String tweetFromMilan="oi mona non fa il bischero";
	private static String tweetFromRome="ao esiste un solo un capitano e se chiama Totti";

	@BeforeClass
	public static void setUp() throws Exception {
		MySQLAccessTest msql = new MySQLAccessTest();
		msql= new MySQLAccessTest();
		teller= new  CityOfTweetsTellerTest();
		BufferedReader br = new BufferedReader(new FileReader("test/mysqlTestGen.txt"));
		String line;
		while ((line = br.readLine()) != null) {
			if(line.replaceAll("\\s+", "").length()>0)
				msql.insertNewRecord(line);
		}
		
	}

	@Test
	public void getCandidateCitiesTest() throws ClassNotFoundException, SQLException {
		teller.guessThePossibleCities(tweetFromMilan.split(" "));
		String result = teller.getCandidateCities().stream().findFirst().get().getCity().toString();
		assertEquals("Milano,IT", result);
	}

	@Test
	public void getCandidateCities2Test() throws ClassNotFoundException, SQLException {
		teller.guessThePossibleCities(tweetFromRome.split(" "));
		String result = teller.getCandidateCities().stream().findFirst().get().getCity().toString();
		assertEquals("Roma,IT", result);
	}


	@AfterClass
	public static void cleanup() throws SQLException, ClassNotFoundException{
		MySQLAccessTest msql2 = new MySQLAccessTest();
		msql2.insertNewRecord("drop table testTable");

	}
}




