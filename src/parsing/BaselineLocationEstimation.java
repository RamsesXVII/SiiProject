package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaselineLocationEstimation {
	
	private static BufferedReader city2text;

	public static double distribuzioneParole(String citta, String parola) throws IOException{
		FileReader input = new FileReader("resources/city2text.txt");
		city2text = new BufferedReader(input);
		String linea;
		double s=0;
		boolean trovata=false;
		while(((linea=city2text.readLine())!=null)&&!(trovata)){
			if (linea.contains(citta)){
				trovata=true;
				s=calcoloDistribuzione(parola, linea);
			}
		}
		return s;
		
	}
	
	private static double calcoloDistribuzione(String parola, String linea) {
		String[]parole=linea.split("  ||, ");
		double p=parole.length-1;
		int occ=0;
		for(String word:parole){
			if(word.equals(parola))occ++;
		}
		return (occ/p);
	}

	public static String stimatoreBase(String cityList, String tweets) throws IOException{
		String[]cities=cityList.split(", ");
		String[]testo;
		String[]parole;
		double prob;
		Map<String,Double> citta2pro= new HashMap<String,Double>();
		testo=tweets.split("  ");
		parole=testo[1].split(" || , ");
		for(String city: cities){
			prob=0.0;
			for(String parola:parole){
				prob+=distribuzioneParole(city,parola)*(count(parola,parole)/parole.length);
			}
			citta2pro.put(city, prob);
		}
		citta2pro=sortByValue(citta2pro);
		Set<String> citta=citta2pro.keySet();
		String result=null;
		for(int i=0;i<11;i++){
			for(String city:citta){
				result=result+city+" ";
			}
		}
		return result;
	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ){
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>(){
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 ){
				return (o1.getValue()).compareTo( o2.getValue() );
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list){
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}

	private static double count(String parola, String[] parole) {
		double occ=0.0;
		for(String word:parole){
			if(word.equals(parola))occ++;
		}
		return occ;
	}
	
	public static void main(String[]args) throws IOException{
		FileReader input1 = new FileReader("resources/city.txt");
		BufferedReader city = new BufferedReader(input1);
		String citta=city.readLine().toString();
		System.out.println(stimatoreBase(citta,"44444  Recession Budget Stretcher economy slowly"
				+ " making back matter choses call , Glad prove chance check site col , "
				+ "Recession Budget Stretcher economy slowly making back matter choses call ,"
				+ " hit 3000 views mark today follow hope you're reading articles god info azine ,"
				+ " Interesting , unfortunate dream , Understanding Users Social Networks HBS Twitter ,"
				+ " talking Pats reason part statistic LET'S PATS , Happy Sunday"));
	}

}