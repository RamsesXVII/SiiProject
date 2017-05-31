package parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class City2Text {

	public City2Text(){}

	public void userJoinCity(String user2cityFile, String city2textFile, String tweets) throws IOException{

		FileReader input1 = new FileReader(user2cityFile);
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

		File out1 = new File(city2textFile);
		FileWriter fw1 = new FileWriter(out1,true);
		BufferedWriter city2text = new BufferedWriter(fw1);

		FileReader input2 = new FileReader(tweets);
		BufferedReader tweetParsed = new BufferedReader(input2);

		String lineaTweet=null;

		while((lineaTweet = tweetParsed.readLine())!=null){
			String[] fields = lineaTweet.split("\\|EndOfUserID\\|");
			String userID = fields[0].replaceAll(" ", "");
			String tweetText = fields[1];
			if (user2city.get(userID)!=null)
				city2text.write(user2city.get(userID) + " |EndOfCityID| " + tweetText+"\n");
		}

		tweetParsed.close();
		city2text.close();
	}	
}

