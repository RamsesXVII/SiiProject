package validation;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

public class City2Score implements Comparable<City2Score> {
	private String latLngCity;
	private double[] cityLatLng;
	//private String state;
	private Double score;




	public City2Score(String city, Double score) {
		super();
		this.latLngCity=city;
		this.score=score;
	}


	public double[] getLatLngForAddr(String addr) {

		if (addr == null) return null;

		Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest;
		GeocodeResponse geocoderResponse;
		geocoderRequest = new GeocoderRequestBuilder().setAddress(addr).setLanguage("en").getGeocoderRequest();
		geocoderResponse = geocoder.geocode(geocoderRequest);
		if (geocoderResponse != null) {
			if (geocoderResponse.getStatus() == GeocoderStatus.OK) {
				if (!geocoderResponse.getResults().isEmpty()) {
					GeocoderResult geocoderResult = // Get the first result
							geocoderResponse.getResults().iterator().next();
					double[] loc = new double[2];
					LatLng ll = geocoderResult.getGeometry().getLocation();
					loc[0] = ll.getLat().doubleValue();
					loc[1] = ll.getLng().doubleValue();
					return loc;
				}
			}
		}
		return null;
	}


	public void sumScore(City2Score city2) {
		if(city2.getCity().equals(this.latLngCity))
			this.score+=city2.getScore();

	}


	public double[] findCoordinateOld() {
		this.cityLatLng = getLatLngForAddr(this.getCity());
		return this.cityLatLng;

	}
	
	public double[] findCoordinate() {
		String[] split = this.getCity().split("$");
		this.cityLatLng[0]=new Double(split[0]);
		this.cityLatLng[1]=new Double(split[1]);
		return this.cityLatLng;

	}



	public String getCity() {
		return latLngCity;
	}

	public void setCity(String city) {
		this.latLngCity = city;
	}


	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}


	public double[] getCityLatLng() {
		return cityLatLng;
	}


	public void setCityLatLng(double[] cityLatLng) {
		this.cityLatLng = cityLatLng;
	}


	@Override
	public int compareTo(City2Score o) {
		int compare = Double.compare(this.getScore(),o.getScore());
		return -1*compare;
	}

	@Override
	public boolean equals(Object obj) {
		City2Score o= (City2Score) obj;
		return this.getCity().equals(o.getCity());

	}
	@Override
	public String toString(){
		String coordinate="";
		if(this.cityLatLng!=null){
			coordinate= " cordinate: "+this.cityLatLng[0]+","+this.cityLatLng[1];
		}
		return "citta: "+this.latLngCity+" score:"+this.score+coordinate;
	}






}
