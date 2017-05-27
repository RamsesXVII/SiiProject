package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CityAndStateParser {
	private Map<String,Map<String,Integer>> city2occurrence;
	private Map<String,String> bestState4city;
	private Map<String,Map<String,Integer>> shortCutMap;
	private Map<String,String> bestShortCut4State;


	public CityAndStateParser(String occOfCitiesInPath) throws IOException{

		FileReader input = new FileReader(occOfCitiesInPath);
		BufferedReader lines= new BufferedReader(input);

		this.city2occurrence = new HashMap<String, Map<String,Integer>>();
		this.shortCutMap = new HashMap<String, Map<String,Integer>>();
		this.bestState4city= new HashMap<String, String>();
		this.bestShortCut4State= new HashMap<String, String>();

		aggregateCity(lines);
		buildTheParser();


	}


	private void buildTheParser() {
		for(String city: city2occurrence.keySet()){
			Map<String,Integer> possibleState = city2occurrence.get(city);
			List<String> longer= new LinkedList<String>();
			if(possibleState.keySet().size()>1){				
				for(String long_name :possibleState.keySet()){
					if(long_name.length()>3){
						for(String shortName: possibleState.keySet()){
							Integer sum = possibleState.get(shortName)+possibleState.get(long_name);
							if(isReducible(long_name, shortName)){
								possibleState.put(shortName, sum);
								longer.add(long_name);
								addToShortCuts(long_name,shortName);
							}
						}
					}
				}
			}

			for(String long_name: longer){
				city2occurrence.get(city).remove(long_name);
			} 

			String candidateState="";
			int candidate=-1;
			for(String best:possibleState.keySet()){
				if(possibleState.get(best)>candidate && !best.equals("##")){
					candidate=possibleState.get(best);
					candidateState=best;
				}
			}
			bestState4city.put(city, candidateState);
		}

		chooseTheShortCuts();

	}


	private void chooseTheShortCuts() {

		for(String longState: this.shortCutMap.keySet()){
			String candidateState="";
			int candidate=-1;
			Map<String, Integer> state2ShortCut = this.shortCutMap.get(longState);
			for(String shortState: state2ShortCut.keySet()){
				if(state2ShortCut.get(shortState)>candidate){
					candidate=state2ShortCut.get(shortState);
					candidateState=shortState;
				}
			}
			this.bestShortCut4State.put(longState, candidateState);
		}



	}


	private void addToShortCuts(String long_name, String shortName) {

		Map<String, Integer> shortCuts = this.shortCutMap.get(long_name);
		if(shortCuts==null){
			shortCuts=new HashMap<String, Integer>();
		}
		if(shortCuts.containsKey(shortName)){
			Integer used=shortCuts.get(shortName)+1;
			shortCuts.put(shortName, used);
		}
		else{
			shortCuts.put(shortName, 1);
		}
		shortCutMap.put(long_name, shortCuts);

	}


	private boolean isReducible(String long_name, String shortName) {
		return !shortName.equals(long_name) && shortName.length()<3&& long_name.substring(0,1)
				.equals(shortName.substring(0,1));
	}


	private void aggregateCity(BufferedReader lines) throws IOException {
		String currentLine=lines.readLine();
		do{
			String city= currentLine.substring(currentLine.indexOf(' ')+1);
			Integer occ= new Integer(currentLine.substring(0,currentLine.indexOf(' ')));
			String default_state= "##";
			int stateIndx=city.indexOf(",");
			
			if(stateIndx!=-1){
				default_state=city.substring(stateIndx+2);
				city=new String(city.substring(0,stateIndx));
			this.bestState4city.put(city, default_state);	
			}
			

			Map<String,Integer> stateList = city2occurrence.get(city);

			if(stateList==null){
				stateList= new HashMap<String,Integer>();}

			stateList.put(default_state,occ);
			city2occurrence.put(city, stateList);			
			currentLine=lines.readLine();
		}while(currentLine!=null);


	}


	public String bestState4UntaggedCity(String city_name){
		String toReturn = this.bestState4city.get(city_name);
		if(toReturn!=null&&!(toReturn.equals("")))
			return toReturn;
		else 
			return city_name;

	}

	public String bestAbbreviaton4State(String state_name){
		String toReturn = this.bestShortCut4State.get(state_name);
		if(toReturn!=null&&!(toReturn.equals("")))
			return toReturn;
		else 
			return state_name;
	}


	public Map<String, String> getBestState4city() {
		return bestState4city;
	}


	public void setBestState4city(Map<String, String> bestState4city) {
		this.bestState4city = bestState4city;
	}


	public Map<String, String> getBestShortCut4State() {
		return bestShortCut4State;
	}


	public void setBestShortCut4State(Map<String, String> bestShortCut4State) {
		this.bestShortCut4State = bestShortCut4State;
	}

	
	
}
