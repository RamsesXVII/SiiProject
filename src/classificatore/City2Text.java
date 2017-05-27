package classificatore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class City2Text {
		
	//public void city2text()throws IOException{
	public static void main(String[] args) throws IOException{
	
		FileReader input1 = new FileReader("resources/city2user.txt");
		BufferedReader city2user = new BufferedReader(input1);
		
		File out1 = new File("resources/city2text.txt");
		FileWriter fw1 = new FileWriter(out1,true);
		BufferedWriter city2text = new BufferedWriter(fw1);
		
		String lineaCity=null;
		String lineaTweet=null;
		String[] cityUtenti=null;
		String[] tweet=null;
		
		while ((lineaCity=city2user.readLine())!=null){ 
			cityUtenti = lineaCity.split("\\t|\\, ");
			city2text.write(cityUtenti[0] + " EndOfCity ");
			
			for(int i=1; i<cityUtenti.length; i++){
				FileReader input2 = new FileReader("resources/tweet_parsati.txt");
				BufferedReader tweetParsed = new BufferedReader(input2);
				
				while((lineaTweet = tweetParsed.readLine())!=null){
					tweet = lineaTweet.split("\\s+");
					
					if(cityUtenti[i].equals(tweet[0])){
						
						for(int y=1; y<tweet.length; y++){
							city2text.write(tweet[y]+" ");
						}
						
						city2text.write(", ");					
					}
				}
				tweetParsed.close();				
			}
			city2text.write("\n");
			city2text.flush();
		}
		city2text.close();
		city2user.close();
	}
}

