package tfIdfUtility;

import java.util.HashMap;
import java.util.HashSet;



public class main {

	public static void main(String[] args) throws Exception {

		
		CityToCountUtility cUtility=new CityToCountUtility();
		cUtility.addItemsFromFile("resources/iodice.txt");
		
		HashMap<String, HashSet<CityToCount>>  words2citiesToCount=cUtility.getWordToCityCount();
		System.out.println("****************************");

		HashMap<String,HashSet<WordToCount>> cities2wordsToCount=cUtility.convertToCitiesToWords(words2citiesToCount);
		System.out.println("****************************");

		TermFrequencyCalculator tfCalc= new TermFrequencyCalculator(words2citiesToCount, cities2wordsToCount);
		HashMap<String,HashMap<String,Double>>word2cityTfIdf=tfCalc.getAndPersistTfIdfMap();
		
		
		System.out.println("****************************");
	}
}
