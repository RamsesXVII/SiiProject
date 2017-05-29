package tfIdfUtility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CityToCountUtility {
	private HashMap<String, HashSet<CityToCount>> wordToCityCount;

	public CityToCountUtility(){
		this.wordToCityCount=new HashMap<String, HashSet<CityToCount>>();
	}

	/**
	 * Add a new record to the map
	 * @param wordToInsert
	 * @param cityName
	 */
	public void addWordToCityCount(String wordToInsert, String cityName){
		if(wordToCityCount.containsKey(wordToInsert)){

			boolean cityAlreadyPresent=false;
			HashSet<CityToCount> cities=wordToCityCount.get(wordToInsert);
			CityToCount currentCity= new CityToCount(cityName, 1);

			for(CityToCount cityPresent:cities){
				if(cityPresent.equals(currentCity)){
					cityAlreadyPresent=true;
					cityPresent.increment();
					break;
				}
			}
			if(!cityAlreadyPresent)
				cities.add(currentCity);
		}       

		else{
			HashSet<CityToCount>citiesToCount=new HashSet<>();
			CityToCount currentCity= new CityToCount(cityName, 1);
			citiesToCount.add(currentCity);
			wordToCityCount.put(wordToInsert, citiesToCount);
		}
	}

	/**
	 * Populate the map from file
	 * Lines must be in form "City 'EndOfCity' tweet"
	 * @param pathToFile
	 */
	public void addItemsFromFile(String pathToFile){
		int counter=0;
		try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				counter++;
				if(counter%100000==0)
					System.out.println(counter);

				String[]splittedLine=line.split("\\|EndOfCityID\\|");
				//System.out.println(splittedLine);
				String city=splittedLine[0];
				String[] splittedTweet=splittedLine[1].split("\\s+");

				for(String tweet : splittedTweet)
					if(tweet.length()>1)
						this.addWordToCityCount(tweet.replaceAll("\\s+", "").toLowerCase(), city.replaceAll("\\s+", ""));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File non trovato, va nella cartella più esterna");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, HashSet<CityToCount>> getWordToCityCount() {
		return wordToCityCount;
	}


	public void setWordToCityCount(HashMap<String, HashSet<CityToCount>> wordToCityCount) {
		this.wordToCityCount = wordToCityCount;
	}


	/**
	 * Convert word2citiesCount to cities2wordCount. Necessary to compute idf.
	 * @param words2citiesToCount
	 * @return
	 */
	public HashMap<String, HashSet<WordToCount>> convertToCitiesToWords(HashMap<String, HashSet<CityToCount>> words2citiesToCount) {
		HashMap<String, HashSet<WordToCount>> cities2words2count=new HashMap<String, HashSet<WordToCount>>();

		Set<String> words=words2citiesToCount.keySet();

		for(String word:words){
			Set<CityToCount>citiesToCount=words2citiesToCount.get(word);

			for(CityToCount city:citiesToCount){
				if(cities2words2count.containsKey(city.getCity())){
					cities2words2count.get(city.getCity()).add(new WordToCount(word, city.getCount()));
				}
				else{
					HashSet<WordToCount>newWords= new HashSet<WordToCount>();
					newWords.add(new WordToCount(word, city.getCount()));
					cities2words2count.put(city.getCity(),newWords);
				}
			}
		}
		return cities2words2count;
	}

}
