//package tfIdfUtility;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//
//public class TermFrequencyCalc {
//	private HashMap<String, HashSet<CityToCount>>  words2citiesToCount;
//	private HashMap<String,HashSet<WordToCount>> cities2wordsToCount;
//	private int numberOfCitiesTotal;
//	private MySQLAccess mAccess;
//
//	public TermFrequencyCalc(HashMap<String, HashSet<CityToCount>> words2citiesToCount,
//			HashMap<String,HashSet<WordToCount>> cities2wordsToCount) throws ClassNotFoundException, SQLException{
//		this.words2citiesToCount=words2citiesToCount;
//		this.cities2wordsToCount=cities2wordsToCount;
//		this.numberOfCitiesTotal=cities2wordsToCount.keySet().size();
//		this.mAccess=new MySQLAccess();
//	}
//	
//	public void persistTfIdfMap() throws SQLException{
//		Set<String> words=words2citiesToCount.keySet();
//		for(String word:words){
//			Set<CityToCount>cities2Count=this.words2citiesToCount.get(word);
//			int numberOfCitiesByTerm=cities2Count.size();
//			for(CityToCount city:cities2Count){
//				int maxOccurrencies=computeMaxOccurrencies(this.cities2wordsToCount.get(city));
//
//				int numberOfOccurrencies=city.getCount();
//				double tfIdf=computeTfIdf(numberOfOccurrencies,maxOccurrencies,this.numberOfCitiesTotal,numberOfCitiesByTerm);
//				mAccess.peristIdf(word,city.getCity(),tfIdf);
//			}
//		}
//	}
//
//	private double computeTfIdf(Integer count, int maxOccurrencies, int numberOfCitiesTotal, int numberOfCitiesByTerm) {
//		double tf= (count*1.0)/(maxOccurrencies*1.0);
//		double idf=Math.log(numberOfCitiesTotal*1.0)/(numberOfCitiesByTerm*1.0);
//		return idf*tf;
//	}
//
//	private int computeMaxOccurrencies(Set<WordToCount> words2Count) {
//		int max=-1;
//		for(WordToCount word: words2Count){
//			if(word.getCount()>max)
//				max=word.getCount();
//		}
//		return max;
//	}
//
//
//
//}
