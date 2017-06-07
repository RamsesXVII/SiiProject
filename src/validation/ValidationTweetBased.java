package validation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ValidationTweetBased extends Validation{


	public ValidationTweetBased(){
		super();
	}

	@Override
	public void writeBestCity(String outPath) throws ClassNotFoundException, SQLException, IOException{

		CityOfTweetsExpert teller= new CityOfTweetsExpert(K);

		File out = new File(outPath);
		FileWriter fw1 = new FileWriter(out,true);
		BufferedWriter bw1 = new BufferedWriter(fw1);
		BufferedReader br = new BufferedReader(new FileReader("resources/test_set_tweets_parsati.txt"));
		User2tweetBuilder user2TweetB= new  User2tweetBuilder("resources/test_set_tweets_parsati.txt",
				"resources/test_set_users.txt");

		String line;
		while ((line = br.readLine()) != null) {
			try{
				accuracy=0;
				String[] splitted = line.split("\\|EndOfUserID\\|");
				String tweet = splitted[1];
				String user= splitted[0]; 
				bw1.write("tweet corrente: "+tweet+"\n");
				
				double[] testPosition = user2TweetB.getUser2City().get(user);
				writeBestGuessed(teller,tweet,testPosition,bw1);
				int numbTweet=user2TweetB.getUser2TweetsCount2().get(user);
				accuracy= accuracy/numbTweet ;
				
				String output = "accuratezza :" + accuracy;
				bw1.write(output+"\n");
				bw1.flush();
				
			}catch(Exception e){
				System.out.println("malformattato");
			}
		}

		

		System.out.println("entro i 100km ne becco: " +entroi160);
		Double media= distances.stream().mapToDouble((x)->x).average().getAsDouble();
		System.out.println("la media e "+ media);
		bw1.close();
		br.close();


	}



}
