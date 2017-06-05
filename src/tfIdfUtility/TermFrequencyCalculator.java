package tfIdfUtility;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import tfIdfUtility.CityToCount;
import tfIdfUtility.MySQLAccess;
import tfIdfUtility.WordToCount;

/*
 * Class to support test. Use a map instead of mysql.
 */
public class TermFrequencyCalculator {
	private HashMap<String, HashSet<CityToCount>>  words2citiesToCount;
	private HashMap<String,HashSet<WordToCount>> cities2wordsToCount;
	private int numberOfCitiesTotal;
	private MySQLAccess mAccess;

	public TermFrequencyCalculator(HashMap<String, HashSet<CityToCount>> words2citiesToCount,
			HashMap<String,HashSet<WordToCount>> cities2wordsToCount) throws ClassNotFoundException, SQLException{
		this.words2citiesToCount=words2citiesToCount;
		this.cities2wordsToCount=cities2wordsToCount;
		this.numberOfCitiesTotal=cities2wordsToCount.keySet().size();
		this.mAccess= new MySQLAccess();
	}

	public HashMap<String,HashMap<String,Double>> getAndPersistTfIdfMap() throws SQLException {

		HashMap<String,HashMap<String,Double>>word2cityTfIdf=new HashMap<String,HashMap<String,Double>>();
		Set<String> words=words2citiesToCount.keySet();
		
		System.out.println("numero parole "+words.size()); 
		HashMap<String,Double> citiesToScore=new HashMap<>();
		int counter=0;

		for(String word :words){
			citiesToScore=new HashMap<>();
			counter++;
			if(counter%100000==0)
				System.out.println(counter);

			Set<CityToCount>cities2Count=this.words2citiesToCount.get(word);
			int numberOfCitiesByTerm=cities2Count.size();
			for(CityToCount city:cities2Count){
				int maxOccurrencies=computeMaxOccurrencies(this.cities2wordsToCount.get(city.getCity()));

				int numberOfOccurrencies=city.getCount();
				double tfIdf=computeTfIdf(numberOfOccurrencies,maxOccurrencies,this.numberOfCitiesTotal,numberOfCitiesByTerm);
				citiesToScore.put(city.getCity(), tfIdf);
			}
			this.mAccess.persistfIdfFromMap1(word, citiesToScore);

		}
		this.mAccess.close();
		return word2cityTfIdf;
	}

	private double computeTfIdf(Integer count, int maxOccurrencies, int numberOfCitiesTotal, int numberOfCitiesByTerm) {
		double tf= (count*1.0)/(maxOccurrencies*1.0);
		double idf=Math.log((numberOfCitiesTotal*1.0)/(numberOfCitiesByTerm*1.0));
		return idf*tf;
	}

	private int computeMaxOccurrencies(Set<WordToCount> words2Count) {
		int max=-1;
		for(WordToCount word: words2Count){
			if(word.getCount()>max)
				max=word.getCount();
		}
		return max;
	}



}
