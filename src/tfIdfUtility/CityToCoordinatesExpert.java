package tfIdfUtility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import classificatore.FunzioneFocusDispersione;
/**
 * Class to associate each city to its latidute/longitude coordinates. 
 * It uses google geocodar api. To avoid to many requests, 150K pairs are updated from
 * datasetFromStrem/usCity2coordinates.csv. A  request is  performed only if the map doesn't contain
 * the link. 
 * @author IORIDI
 *
 */
public class CityToCoordinatesExpert {

	private String pathToFileUsCity2Coordinates;
	private TreeMap<String,String> city2Coordinates;
	private int requestCounter=0;

	public CityToCoordinatesExpert(String pathToFileUsCity2Coordinates) {
		this.pathToFileUsCity2Coordinates=pathToFileUsCity2Coordinates;
		this.city2Coordinates= new TreeMap<>();

	}

	/**
	 * From datasetFromStrem/usCity2coordinates.csv city-coordinates pairs are put into 
	 * city2Coordinates map in the form of city->lat$long.
	 * Example of record in the file is:
	 * pasadena;Pasadena;CA;34.1477778;-118.1436111
	 * Example of record in the map is:
	 * Pasadena,CA->34.1477778$-118.1436111
	 * @throws IOException
	 */
	public void computeCity2Coordinates() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(this.pathToFileUsCity2Coordinates))) {
			String line;
			while ((line = br.readLine()) != null) {

				String[] splittedLine= line.split(";");
				String city=splittedLine[1].replaceAll("\\s+", "").toLowerCase();
				String state=splittedLine[2].replaceAll("\\s+", "").toLowerCase();
				String latitude=splittedLine[3];
				String longitude=splittedLine[4];

				String city2state= city+ "," + state;
				String latitudeToLongitude=latitude+"$"+longitude;

				this.city2Coordinates.put(city2state, latitudeToLongitude);

			}

		}
	}

	public TreeMap<String,String> getCity2Coordinates(){
		return this.city2Coordinates;
	}


	/**
	 * After having filled the map with city-coordinates links present into datasetFromStrem/usCity2coordinates.csv
	 * it's possibile to perform a get on the map. If the city is not found it's performed a request to google geocoder.
	 * Map is updated with new values. 
	 * @param city
	 * @return
	 */
	public String getCoordinate(String city){

		city=city.replaceAll("\\s+", "").toLowerCase();

		if(this.city2Coordinates.containsKey(city)){
			return city2Coordinates.get(city);
		}

		else{
			System.out.println(" non ci sta");
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
}