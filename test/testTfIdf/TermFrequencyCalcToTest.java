package testTfIdf;

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
public class TermFrequencyCalcToTest {
	private HashMap<String, HashSet<CityToCount>>  words2citiesToCount;
	private HashMap<String,HashSet<WordToCount>> cities2wordsToCount;
	private int numberOfCitiesTotal;

	public TermFrequencyCalcToTest(HashMap<String, HashSet<CityToCount>> words2citiesToCount,
			HashMap<String,HashSet<WordToCount>> cities2wordsToCount) throws ClassNotFoundException, SQLException{
		this.words2citiesToCount=words2citiesToCount;
		this.cities2wordsToCount=cities2wordsToCount;
		this.numberOfCitiesTotal=cities2wordsToCount.keySet().size();
	}
	
	public HashMap<String,HashMap<String,Double>> getTfIdfMap() {
		HashMap<String,HashMap<String,Double>>word2cityTfIdf=new HashMap<String,HashMap<String,Double>>();
		Set<String> words=words2citiesToCount.keySet();
		for(String word :words){
		//	System.out.println("parola "+word);
			Set<CityToCount>cities2Count=this.words2citiesToCount.get(word);
			int numberOfCitiesByTerm=cities2Count.size();
			for(CityToCount city:cities2Count){
				int maxOccurrencies=computeMaxOccurrencies(this.cities2wordsToCount.get(city.getCity()));
				
				int numberOfOccurrencies=city.getCount();
				double tfIdf=computeTfIdf(numberOfOccurrencies,maxOccurrencies,this.numberOfCitiesTotal,numberOfCitiesByTerm);
		//		HashMap<String,Double>cityToIdf=new HashMap<>();
			//	cityToIdf.put(city.getCity(),new Double(tfIdf), new Double(tfIdf));
			//	word2cityTfIdf.put(word,cityToIdf);
				insertItem(word,city.getCity(),new Double(tfIdf), word2cityTfIdf);
			//	mAccess.peristIdf(word,city.getCity(),tfIdf);
			}
		}
		return word2cityTfIdf;
	}

	private void insertItem(String word, String city, Double tfIdf,HashMap<String, HashMap<String, Double>> word2cityTfIdf) {
		if(word2cityTfIdf.containsKey(word)){
			HashMap<String, Double> citiesToIdf=word2cityTfIdf.get(word);
			citiesToIdf.put(city, tfIdf);
		}
		else{
			HashMap<String, Double> citiesToIdf=new HashMap<>();
			citiesToIdf.put(city, tfIdf);
			word2cityTfIdf.put(word, citiesToIdf);
			
		}
		
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
