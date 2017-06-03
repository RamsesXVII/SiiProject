package cityToCoordinates;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


public class CityToCoordinateTest {

	private String pathToFileUsCity2Coordinates="datasetFromStream/usCity2Coordinates.csv";
	private String city2CoordinatesToConvert="test/city2CoordinatesToConvert.txt";

	private String newYorkNY;
	private String newYorkCoordinates;

	private String losAngelesCA;
	private String laCoordinates;
	
	private String pasadenaCA;
	private String pasadenaCoordinates;
	
	private CityToCoordinatesExpertTest cExpert;
	
	private Map<String,String> cityToCoordinates;

	@Before
	public void setUp() throws Exception {
		this.newYorkNY= new String("New York, NY");
		this.newYorkCoordinates= new String("40.7141667$-74.0063889");
		
		this.losAngelesCA=new String("Los Angeles, CA");
		this.laCoordinates=new String("34.0522222$-118.2427778");

		this.pasadenaCA=new String("Pasadena, CA");
		this.pasadenaCoordinates=new String("34.1477778$-118.1436111");
		
		this.cExpert= new CityToCoordinatesExpertTest(pathToFileUsCity2Coordinates,city2CoordinatesToConvert);
		cExpert.computeCity2Coordinates();
		this.cityToCoordinates=cExpert.getCity2Coordinates();
		
	}

	@Test
	public void cityToCoordinatesTest() {

		assertEquals(this.newYorkCoordinates, this.cityToCoordinates.get(this.newYorkNY));
		assertEquals(this.laCoordinates, this.cityToCoordinates.get(this.losAngelesCA));
		assertEquals(this.pasadenaCoordinates, this.cityToCoordinates.get(this.pasadenaCA));
	}

	
	@Test
	public void findNotPresentInFile() throws FileNotFoundException, IOException {
		assertEquals(new String("42.415183$14.2988085"), this.cExpert.getCoordinate("Francavilla al mare"));
		assertEquals(new String("47.497912$19.040235"), this.cExpert.getCoordinate("Budapest"));
		assertEquals(new String("35.6894875$139.6917064"), this.cExpert.getCoordinate("Tokyo"));

		
	}

	
}