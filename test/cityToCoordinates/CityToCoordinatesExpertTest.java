package cityToCoordinates;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeMap;

import classificatore.FunzioneFocusDispersione;

public class CityToCoordinatesExpertTest {

	private String pathToFileCityToConvert;
	private String pathToFileUsCity2Coordinates;
	private TreeMap<String,String> city2Coordinates;

	public CityToCoordinatesExpertTest(String pathToFileUsCity2Coordinates,String pathToFileCityToConvert) {
		this.pathToFileUsCity2Coordinates=pathToFileUsCity2Coordinates;
		this.pathToFileCityToConvert= pathToFileCityToConvert;
		this.city2Coordinates= new TreeMap<>();

	}

	public void computeCity2Coordinates() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(this.pathToFileUsCity2Coordinates))) {
			String line;
			while ((line = br.readLine()) != null) {

				String[] splittedLine= line.split(";");
				String city=splittedLine[1];
				String state=splittedLine[2];
				String latitude=splittedLine[3];
				String longitude=splittedLine[4];

				String city2state= city+ ", " + state;
				String latitudeToLongitude=latitude+"$"+longitude;

				this.city2Coordinates.put(city2state.replaceAll("\\s+", ""), latitudeToLongitude);

			}

		}
	}

	public TreeMap<String,String> getCity2Coordinates(){
		return this.city2Coordinates;
	}

	public String getCoordinate(String city){

		city=city.replaceAll("\\s+", "");

		if(this.city2Coordinates.containsKey(city))
			return city2Coordinates.get(city);

		else{
			System.out.print("non ho a disposizione i dati di ");
			System.out.print(city);
			System.out.println(" interrogo google geocoder");

			double[] coordinates=FunzioneFocusDispersione.getLatLngForAddr(city);
			String lat=coordinates[0]+"";
			String lng=coordinates[1]+"";

			this.city2Coordinates.put(city, lat+"$"+lng);
		}
		return city2Coordinates.get(city);

	}
	/**
	 * Upload the list of city to convert (just to a preliminare test to have an idea of how may city-coordinates links are not 
	 * present in usCity2Coordinates
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public  HashSet<String> getCityToConvertList() throws FileNotFoundException, IOException{
		HashSet<String> cityToConvert  = new HashSet<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.pathToFileCityToConvert))) {
			String line;
			while ((line = br.readLine()) != null) {
				cityToConvert.add(line);
			}
		}
		return cityToConvert;

	}

}
