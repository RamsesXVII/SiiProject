package validation;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import utility.MySQLAccess;

public class CityOfTweetsExpert {
	private String currentTweet;
	private Set<City2Score> candidateCities;

	private Map<String, Map<String, Double>> myWordMap;
	private Map<String, Map<Integer, Double>> myWordLatticeMap;
	private int K_BEST;
	private TreeSet<String> mostFrequencyWord;


	public CityOfTweetsExpert(){
		this.populateMostFrequencyWord();

	}

	public CityOfTweetsExpert(int k) throws ClassNotFoundException, SQLException{
		MySQLAccess mysql= new MySQLAccess();
		System.out.println("inizio popolazione mappa parole");
		myWordMap = mysql.populateWordMap();
		System.out.println("finito popolazione mappa parole");
		System.out.println("inizio popolazione lattice");
		myWordLatticeMap = mysql.populateWordtoLatticeMap();
		System.out.println("fine popolazione lattice");
		this.populateMostFrequencyWord();

	}

	private void populateMostFrequencyWord() {
		this.mostFrequencyWord= new TreeSet<String>();

		try (BufferedReader br = new BufferedReader(new FileReader("dictionary/parole.txt"))) {
			String line;

			while ((line = br.readLine()) != null) {
				line=line.replaceAll("\\s+", "");
				this.mostFrequencyWord.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File non trovato, va nella cartella piu' esterna");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		guessByLattice(words);
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
			System.out.print("con "+ K_BEST+"K ");
			return toReturn.subList(0, Math.min(toReturn.size(),this.K_BEST));
		}
		else return null;
	}

	private Double getLatticeValue(Map<Integer, Double> wordLattice, Integer vicino){
		Double value = wordLattice.get(vicino);
		if (value == null)
			return 0.0;
		return value;
	}


	private void guessByLattice(String[] words) throws ClassNotFoundException, SQLException{
		Map<String,City2Score> takenCity=new TreeMap<String,City2Score>();

		for (String word: words){
			if(this.mostFrequencyWord.contains(word)){
				Map<String, Double> cityToScore = myWordMap.get(word);
				Map<Integer, Double> latticeToScore = myWordLatticeMap.get(word);
				if(cityToScore!=null){
					for(String city: cityToScore.keySet()){
						String[] latLong = city.split("\\$");
						int latitude = Integer.parseInt(latLong[0].split("\\.")[0]);
						int longitude = Integer.parseInt(latLong[1].split("\\.")[0]);

						int lattice = (latitude * 10000) + (longitude * -1);

						double center = latticeToScore.get(lattice) * 0.70;
						double tot = 0.0;
						tot += getLatticeValue(latticeToScore, lattice -10000);
						tot += getLatticeValue(latticeToScore, lattice +10000);
						tot += getLatticeValue(latticeToScore, lattice -1);
						tot += getLatticeValue(latticeToScore, lattice +1);

						double latticeProbability = center + tot * 0.30;

						City2Score currCityScore = new City2Score(city, latticeProbability * 0.25 + cityToScore.get(city) * 0.75);
						if(!takenCity.containsKey(city))
							takenCity.put(city, currCityScore);
						else
							takenCity.get(city).sumScore(currCityScore);
					}

				}
			}
		}
		this.candidateCities.addAll(takenCity.values());
	}


	private void guessThePossibleCities(String[] words) throws ClassNotFoundException, SQLException {
		Map<String,City2Score> takenCity=new TreeMap<String,City2Score>();

		for(String word: words){
			if(this.mostFrequencyWord.contains(word)){

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

	public TreeSet<String> getMostFrequencyWord() {
		return mostFrequencyWord;
	}

	public void setMostFrequencyWord(TreeSet<String> mostFrequencyWord) {
		this.mostFrequencyWord = mostFrequencyWord;
	}

	public void setKBest(int k) {
		this.K_BEST=k;

	}


}





