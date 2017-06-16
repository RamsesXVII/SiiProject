package cassificator;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

public class FunzioneFocusDispersione {

	public static double[] getLatLngForAddr(String addr) {
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
	
	
	public static double[] calcoloMediaPonderata(String[] citta, double[] freq){
		double[] latLong = new double[2];
		double[] mediaPonderata = new double[2];
		int i=0;
		double lat = 0,lon=0;
		double fMedia=0;
		for(String city: citta){
			latLong=getLatLngForAddr(city);
			lat=lat+latLong[0]*freq[i];
			lon=lon+latLong[1]*freq[i];
			i++;
		}
		for(double f:freq){
			fMedia=fMedia+f;
		}
		mediaPonderata[0]=lat/fMedia;
		mediaPonderata[1]=lon/fMedia;
		return mediaPonderata;
	}
	public static double calcoloAlfa (String stringa){
	//public static double calcoloAlfa (String[] citta, double []freq){
		String[] str=stringa.split("   ");
		double[]freqs = null;
		String[]cittas = null;
		String[] str1=str[1].split("; ");
		cittas=new String[str1.length];
		freqs=new double[str1.length];
		for(int i=0; i<str1.length;i++){
			String[]str2=str1[i].split(", ");
			cittas[i]=str2[0];
			freqs[i] = Double.parseDouble(str2[1]);
		}
		double alfa=0;
		double sommaFreq=0;
		double[] mediaPonderata=calcoloMediaPonderata(cittas, freqs);
		//double[] latLong= new double[2];
		for(int i=0; i<cittas.length;i++){
			double[]latLong=getLatLngForAddr(cittas[i]);
			alfa=alfa+((Math.pow((latLong[0]-mediaPonderata[0]),2)+Math.pow((latLong[1]-mediaPonderata[1]),2))*freqs[i]);
		}
		for(double f:freqs){
			sommaFreq=sommaFreq+f;
		}
		return Math.sqrt(alfa/sommaFreq);
	}
	
	public static String calcoloFocus (String linea){
		String[] str =linea.split("   ");
		String centro = null;
		double fMax=0;
		String[] citta2f=str[1].split("; ");
		for(String word:citta2f){
			String[]citta=word.split(", ");
			double f=Double.parseDouble(citta[1]);
			if (f>fMax){
				fMax=f;
				centro=word;
			}
		}
		return centro;
	}
	
	public static void main(String[] args){
		double[] lt= getLatLngForAddr("Orlando");
		double[] lt1= getLatLngForAddr("Tampa");
		for(int i=0; i<lt.length; i++){
			System.out.println(lt[i]);
		}
		for(int i=0; i<lt1.length; i++){
			System.out.println(lt1[i]);
		}
		String str= "can't   Orlando, 0.04807692307692308; Tampa, 0.04878048780487805";
		System.out.println("Focus: "+calcoloFocus(str));
		System.out.println("Alfa: "+calcoloAlfa(str));
	
	}
	
}