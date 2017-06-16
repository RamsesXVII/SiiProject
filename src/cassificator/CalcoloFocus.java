package cassificator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class CalcoloFocus {
	
	public static void main(String[]args) throws IOException{
		
		FileReader input1 = new FileReader("resources/frequenzaParolePerCitta.txt");
		BufferedReader input = new BufferedReader(input1);
		
		File out1 = new File("resources/parola2CittaFreq.txt");
		FileWriter fw1 = new FileWriter(out1,true);
		BufferedWriter output = new BufferedWriter(fw1);
		
		String linea;
		String city;
		String[] words;
		String[] wordFreq;
		String word;
		String freq;
		
		Map<String,String> citta2freq;
		Map<String, Map<String,String>> parole2citta=new HashMap<String,Map<String,String>>();
		
		while((linea=input.readLine())!=null){
			String[] parole=linea.split("  , ");
			if(parole.length>1){
				city=parole[0].trim();
				words=parole[1].split(", ");
				for(int i=0;i<words.length;i++){
					wordFreq=words[i].split(" ");
					word=wordFreq[0].trim();
					freq=wordFreq[1].trim();
					citta2freq=parole2citta.get(word);
					if(citta2freq!=null){
						citta2freq.put(city, freq);
						parole2citta.put(word, citta2freq);
					}
					else {
						citta2freq = new HashMap<String, String>();
						citta2freq.put(city, freq);
						parole2citta.put(word, citta2freq);
					}
				}
			}
			
		}
		input.close();
		
		Set<String> keyParole=parole2citta.keySet();
		for(String chiaveP : keyParole){
			output.write(chiaveP+"   ");
			output.flush();
			citta2freq=parole2citta.get(chiaveP);
			Set<String> keyCitta=citta2freq.keySet();
			for(String chiaveC : keyCitta){
				output.write(chiaveC+" "+citta2freq.get(chiaveC)+", ");
				output.flush();
			}
			output.write("\n");
			output.flush();
		}
		output.close();
	}
}
