package twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class CityUtility {
	private HashSet<String> USAcities;
	private HashSet<String> USAcountries;
	private HashSet<String> USAtop100CitiesByPopulation;

	public CityUtility(){
		this.USAcities=getItemsFromFile("datasetFromStream/USACities.txt");
		this.USAcountries=getItemsFromFile("datasetFromStream/USACountries.txt");
		this.USAtop100CitiesByPopulation=getItemsFromFile("datasetFromStream/100CitiesByPopulation.txt");

	}

	public boolean isUSALocation(String location) {
		location=location.toLowerCase().trim().replaceAll(" ", "");
		
		//prendo solo quelle nella forma 'citta,stato' o solo citta se � una top 100
		if(!this.isUSACountry(location)){
			if(!location.contains(","))
				return this.isABigCity(location);
			return false;}

		location=location.substring(0, location.indexOf(","));
		
		if(!this.isUSACity(location))
			return false;

		return true;

	}


	private boolean isUSACity(String location) {
		return this.USAcities.contains(location);

	}

	private boolean isUSACountry(String location) {
		if(!location.contains(","))
			return false;

		location=location.substring(location.indexOf(",")+1);

		return this.USAcountries.contains(location);
	}

	private boolean isABigCity(String location) {
		return this.USAtop100CitiesByPopulation.contains(location);
	}

	private HashSet<String> getItemsFromFile(String pathToFile) {
		String itemToInsert;
		Scanner file;
		try {
			file = new Scanner(new File(pathToFile));
		} catch (FileNotFoundException e) {
			System.out.println("file non trovato");
			return null;
		}
		HashSet<String> items = new HashSet<>();

		//tutto minuscolo e senza spazi
		while (file.hasNextLine()){
			itemToInsert=file.nextLine().toLowerCase().trim().replaceAll(" ", "");
			items.add(itemToInsert);}
		
		file.close();
		return items;

	}

	public HashSet<String> getUSACities(){
		return this.USAcities;
	}

	public HashSet<String> getUSACountries(){
		return this.USAcountries;
	}


}
