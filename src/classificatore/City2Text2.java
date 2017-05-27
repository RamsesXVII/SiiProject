package classificatore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class City2Text2 {

	//public void city2text()throws IOException{
	public static void main(String[] args) throws IOException{

		FileReader input1 = new FileReader("oddiFile.txt");
		BufferedReader bf1 = new BufferedReader(input1);

		HashMap<String, String> user2city = new HashMap<>();

		String userCity= null;

		while((userCity = bf1.readLine())!=null){
			String[] fields = userCity.split("\\|EndOfUserID\\|");
			String user = fields[0];
			String city= fields[1];
			user2city.put(user, city);
		}

		bf1.close();

		File out1 = new File("city2text.txt");
		FileWriter fw1 = new FileWriter(out1,true);
		BufferedWriter city2text = new BufferedWriter(fw1);

		FileReader input2 = new FileReader("tweet_parsati.txt");
		BufferedReader tweetParsed = new BufferedReader(input2);

		String lineaTweet=null;

		while((lineaTweet = tweetParsed.readLine())!=null){
			String[] fields = lineaTweet.split("\\|EndOfUserID\\|");
			String userID = fields[0].replaceAll(" ", "");
			if(fields.length>1){
				String tweetText = fields[1];
				city2text.write(user2city.get(userID) + " |EndOfCityID| " + tweetText+"\n");
			}
		}



		tweetParsed.close();
		city2text.close();
	}	
}

