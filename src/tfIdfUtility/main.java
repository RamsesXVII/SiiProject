package tfIdfUtility;

import java.util.HashMap;
import java.util.HashSet;



public class main {

	public static void main(String[] args) throws Exception {

		
		CityToCountUtility cUtility=new CityToCountUtility();
		cUtility.addItemsFromFile("city2text.txt");
		
		HashMap<String, HashSet<CityToCount>>  words2citiesToCount=cUtility.getWordToCityCount();
		System.out.println("****************************");

		HashMap<String,HashSet<WordToCount>> cities2wordsToCount=cUtility.convertToCitiesToWords(words2citiesToCount);
		System.out.println("****************************");

		TermFrequencyCalcToUpdate tfCalc= new TermFrequencyCalcToUpdate(words2citiesToCount, cities2wordsToCount);
		HashMap<String,HashMap<String,Double>>word2cityTfIdf=tfCalc.getTfIdfMap();
		
		
		System.out.println("****************************");
		MySQLAccess mAccess= new MySQLAccess();
		mAccess.persistfIdfFromMap(word2cityTfIdf);
		
	}

}
