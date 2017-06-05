package tfIdfUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

public class mainCity2Coordinates {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		
		
		
		CityToCountUtility cUtility=new CityToCountUtility();
		cUtility.addItemsFromFile("city2text.txt"); //qui va city to text
		
		HashMap<String, HashSet<CityToCount>>  words2citiesToCount=cUtility.getWordToCityCount();
		System.out.println("****************************");

		HashMap<String,HashSet<WordToCount>> cities2wordsToCount=cUtility.convertToCitiesToWords(words2citiesToCount);
		System.out.println("****************************");

		TermFrequencyCalculator tfCalc= new TermFrequencyCalculator(words2citiesToCount, cities2wordsToCount);
		tfCalc.getAndPersistTfIdfMap();

	}

}
