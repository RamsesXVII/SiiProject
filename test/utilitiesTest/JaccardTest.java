package utilitiesTest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;

import utility.JaccardCoefficient;


public class JaccardTest {

	private static JaccardCoefficient ja;
	private static LinkedList<String> dictionary;

	@BeforeClass
	public static void setUp() throws Exception {
		String d1 = "book";
		String d2 = "borg";
		String d3 = "spok";
		
		dictionary = new LinkedList<>();
		dictionary.add(d1);
		dictionary.add(d2);
		dictionary.add(d3);
		
		ja = new JaccardCoefficient(dictionary);	
	}

	@Test
	public void getCorrectString() throws ClassNotFoundException, SQLException {
		
		String test = "boook";
		assertEquals("book", ja.getWord(test));
		}
	}





