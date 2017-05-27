package tfIdfUtility;

import java.util.List;

public class TermFrequencyCalcDB {
	private int totalCitiesCount;
	private List<CityToCount> listCitiesToCount;
	
	public TermFrequencyCalcDB(List<CityToCount>listCitiesToCount, int totalCitiesCount){
		this.totalCitiesCount=totalCitiesCount;
		this.listCitiesToCount=listCitiesToCount;
	}
	
	public Double getTfIdf(String city, int totalCitiesCount){
		double occurencyInCurrentCity=-1;
		
		for(CityToCount cityToCount: listCitiesToCount){
			if(cityToCount.getCity().equals(city)){
				occurencyInCurrentCity=cityToCount.getCount();
			}
		}
		
		double tf=occurencyInCurrentCity/this.getMaxOccurency();
		double inverse=Math.log((1.0*this.totalCitiesCount)/(listCitiesToCount.size()*1.0));
		
		return tf*inverse;
		
	}
	
	private double getMaxOccurency(){
		double max=-1;
		for(CityToCount cityToCount:listCitiesToCount){
			if(cityToCount.getCount()>max)
				max=cityToCount.getCount();
		}
		return max;
	}

}
