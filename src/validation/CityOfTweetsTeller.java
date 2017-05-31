package validation;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import tfIdfUtility.MySQLAccess;

public class CityOfTweetsTeller {
	private String currentTweet;
	private Set<City2Score> candidateCities;
	private Map<String, Map<String, Double>> myWordMap;

	public CityOfTweetsTeller() throws ClassNotFoundException, SQLException{
		MySQLAccess mysql= new MySQLAccess();
		System.out.println("inizio popolazione mappa");
		myWordMap = mysql.populateWordMap();
		System.out.println("finito popolazione mappa");

	}

	public double[] guessTweetPosition(String tweet) throws ClassNotFoundException, SQLException{
		this.currentTweet=tweet;
		this.candidateCities=new TreeSet<City2Score>();
		String[] words = tweet.split(" ");
		guessThePossibleCities(words);
		return getBestCityCoordinates();
	}


	private double[] getBestCityCoordinates(){
		if(this.candidateCities!=null && this.candidateCities.size()>0){
			City2Score bestCity = this.candidateCities.stream().findFirst().get();
			System.out.println("la citta migliore e "+bestCity.getCity());
			double[] findCoordinate = bestCity.findCoordinate();
			return findCoordinate;
		}
		else return null;
	}


	private void guessThePossibleCities(String[] words) throws ClassNotFoundException, SQLException {
		Map<String,City2Score> takenCity=new TreeMap<String,City2Score>();

		for(String word: words){
			Map<String, Double> map = myWordMap.get(word);
			List<City2Score> currentCities= new LinkedList<City2Score>();
			if(map!=null){
				map.entrySet().forEach(e->currentCities.add(new City2Score(e.getKey(), e.getValue())));
			}	

			for(City2Score city:currentCities){
				if(takenCity.containsKey(city.getCity())){
					takenCity.get(city.getCity()).sumScore(city);
				}else{
					takenCity.put(city.getCity(), city);
				}
			}
		}
		this.candidateCities.addAll(takenCity.values());
	}


	public String getCurrentTweet() {
		return currentTweet;
	}


	public void setCurrentTweet(String currentTweet) {
		this.currentTweet = currentTweet;
	}


	public Set<City2Score> getCandidateCities() {
		return candidateCities;
	}


	public void setCandidateCities(Set<City2Score> candidateCities) {
		this.candidateCities = candidateCities;
	}


}





