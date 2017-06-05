package utilitiesTest;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import validation.City2Score;

public class CityOfTweetsExpertTest {
	private String currentTweet;
	private Set<City2Score> candidateCities;

	public void guessThePossibleCities(String[] words) throws ClassNotFoundException, SQLException {
		MySQLAccessTest mysql= new MySQLAccessTest();
		Map<String,City2Score> takenCity=new TreeMap<String,City2Score>();
		this.candidateCities=new TreeSet<City2Score>();
		Map<String, Map<String, Double>> myWordMap = mysql.populateWordMap();
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





