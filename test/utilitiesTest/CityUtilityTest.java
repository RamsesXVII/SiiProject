package utilitiesTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import twitter.CityUtility;

public class CityUtilityTest {

	private CityUtility cUtility;
	
	private String newyork; //big cities
	private String buffalo;
	private String washington;

	private String rome; //small cities
	private String paris;
	private String gulfport;
	
	
	private String newYorkUSAState;
	private String losAngelesUSAState;
	
	private String baltimoreNotUSAState;
	private String baltimoreUSAState;


	@Before
	public void setUp() throws Exception {

		this.cUtility= new CityUtility();
		
		this.newyork= "New York";
		this.rome= "Rome";
		this.paris= "Paris";
		this.washington= "Washington";
		this.gulfport="gulfport";
		
		this.buffalo="buffalo";
		
		this.newYorkUSAState="new york,NY";
		this.losAngelesUSAState="los angeles, California";
		
		this.baltimoreNotUSAState="baltimore , Swiss";		
		this.baltimoreUSAState="baltimore, Texas";


	}

	@Test
	public void isUSAcity() {

		assertTrue(this.cUtility.isUSALocation(washington));
		assertTrue(this.cUtility.isUSALocation(newyork));
		assertTrue(this.cUtility.isUSALocation(buffalo));


		assertTrue(this.cUtility.isUSALocation(newYorkUSAState));
		assertTrue(this.cUtility.isUSALocation(losAngelesUSAState));
		assertTrue(this.cUtility.isUSALocation(newYorkUSAState));
		assertTrue(this.cUtility.isUSALocation(baltimoreUSAState));

	}
	
	@Test
	public void isNotUSAcity() {

		assertFalse(this.cUtility.isUSALocation(rome));
		assertFalse(this.cUtility.isUSALocation(paris));
		assertFalse(this.cUtility.isUSALocation(gulfport));

		assertFalse(this.cUtility.isUSALocation(baltimoreNotUSAState));
		
	}

}