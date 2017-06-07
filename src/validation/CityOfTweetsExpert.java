package validation;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import tfIdfUtility.MySQLAccess;

public class CityOfTweetsExpert {
	private String currentTweet;
	private Set<City2Score> candidateCities;
	private Map<String, Map<String, Double>> myWordMap;
	private int K_BEST;




	public CityOfTweetsExpert(int k) throws ClassNotFoundException, SQLException{
		MySQLAccess mysql= new MySQLAccess();
		this.K_BEST=k;
		System.out.println("inizio popolazione mappa");
		myWordMap = mysql.populateWordMap();
		System.out.println("finito popolazione mappa");

	}

	/**
	 * @param tweet parsato
	 * @return lista delle 3 citta piu probabili
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<City2Score> guessTweetPositionList(String tweet) throws ClassNotFoundException, SQLException {
		this.currentTweet=tweet;
		this.candidateCities=new TreeSet<City2Score>();
		String[] words = tweet.split(" ");
		guessThePossibleCities(words);
		return getBestCityCoordinatesList();

	}

	/**
	 * latitudine e longitudine della citta più probabile
	 * @param tweet un Tweet parsato
	 * @return coordinate LatLong 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public double[] guessTweetPosition(String tweet) throws ClassNotFoundException, SQLException{
		this.currentTweet=tweet;
		this.candidateCities=new TreeSet<City2Score>();
		String[] words = tweet.split(" ");
		guessThePossibleCities(words);
		return getBestCityCoordinates();
	}


	/**
	 * chiede al Geocoder di trovare le coordinate
	 * @return coordinate lat long
	 */
	private double[] getBestCityCoordinates(){
		if(this.candidateCities!=null && this.candidateCities.size()>0){
			City2Score bestCity = this.candidateCities.stream().findFirst().get();
			double[] findCoordinate = bestCity.findCoordinate();
			return findCoordinate;
		}
		else return null;
	}

	/**
	 * prende al massimo le 3 citta piu probabili da candidateCities
	 * @return 3BestCities le 3 citta migliori per il tweet
	 */
	private List<City2Score> getBestCityCoordinatesList(){
		if(this.candidateCities!=null && this.candidateCities.size()>0){
			List<City2Score> toReturn = new LinkedList<City2Score>();
			this.candidateCities.stream().forEach(e->toReturn.add(e));
			return toReturn.subList(0, Math.min(toReturn.size(),this.K_BEST));

		}
		else return null;
	}


	/**
	 * popola candidateCities con le citta più adatte per le words passate
	 * @param words le parole del tweet
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void guessThePossibleCitiesOld(String[] words) throws ClassNotFoundException, SQLException {
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


	private void guessThePossibleCities(String[] words) throws ClassNotFoundException, SQLException {
		Map<String,City2Score> takenCity=new TreeMap<String,City2Score>();

		for(String word: words){
			Map<String, Double> cityEScore = myWordMap.get(word);
			if(cityEScore!=null){
				for(String city: cityEScore.keySet()){
					City2Score currCityScore = new City2Score(city, cityEScore.get(city));
					if(!takenCity.containsKey(city))
						takenCity.put(city, currCityScore);
					else
						takenCity.get(city).sumScore(currCityScore);
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


	public Map<String, Map<String, Double>> getMyWordMap() {
		return myWordMap;
	}


}





