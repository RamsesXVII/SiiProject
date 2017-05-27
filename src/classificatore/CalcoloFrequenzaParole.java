package classificatore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class CalcoloFrequenzaParole {
	
	private static BufferedReader file;
	private static BufferedWriter bw1;
	
	private static void calcolaFrequenzaParole(String tweet) throws IOException {
		String[] lista=tweet.split(",");
		double numeroTweet=lista.length;
		String[]parole=tweet.replaceAll(" ,", "").split(" ");
		
		for(int i=0; i<parole.length; i++){
			if(parole[i]!=null){
				double occorrenza=1;
				for(int y=i+1; y<parole.length; y++){
					if(parole[i].equals(parole[y])){
						occorrenza++;
						parole[y]=null;
					}
				}
				double f=(occorrenza/numeroTweet);
				bw1.write(", "+parole[i]+" "+f);
			}
		}
	}


	public static void main(String []args) throws IOException{
		
		FileReader input1 = new FileReader("resources/city2text.txt");
		file = new BufferedReader(input1);
		
		File out1 = new File("resources/frequenzaParolePerCitta.txt");
		FileWriter fw1 = new FileWriter(out1,true);
		bw1 = new BufferedWriter(fw1);
		
		String tweet=null;	
		String[] city2text=null;
		
		while((tweet= file.readLine())!=null){
			//Mi scrivo la città
			city2text=tweet.split("  ");
			bw1.write(city2text[0]+"  ");
			
			if(city2text.length>1)calcolaFrequenzaParole(city2text[1]);	
			bw1.write("\n");
			bw1.flush();
		}
		bw1.close();
		file.close();	
	}	
}