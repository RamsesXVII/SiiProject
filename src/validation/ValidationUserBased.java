package validation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ValidationUserBased extends Validation{

	public ValidationUserBased(){
		super();
	}


	@Override
	public void writeBestCity(String outPath) throws IOException, ClassNotFoundException, SQLException{

		File out = new File(outPath);
		FileWriter fw1 = new FileWriter(out,true);
		BufferedWriter bw = new BufferedWriter(fw1);

		CityOfTweetsExpert teller= new CityOfTweetsExpert(K);
		User2tweetBuilder user2TweetB= new  User2tweetBuilder("resources/test_set_tweets_parsati.txt",
				"resources/test_set_users.txt");
		Map<String, double[]> user2City = user2TweetB.getUser2City();

		user2TweetB.computerUser2tweet();
		Map<String, String> user2tweet = user2TweetB.getUser2tweet();
		int counter=0;
		for(String user: user2tweet.keySet()){
			counter++;
			System.out.println("utente: "+user);
			accuracy=0;
			String longTweet=user2tweet.get(user);
			double[] tweetPos=user2City.get(user);
			super.writeBestGuessed(teller, longTweet, tweetPos, bw);
			int numbTweet=user2TweetB.getUser2TweetsCount().get(user);
			//accuracy= accuracy/numbTweet ;
			String output = "azzeccati entro i 160 "+numbTweet+": "+ accuracy;
			//System.out.println("accuracy: "+accuracy );
			bw.write(output+"\n\n");
			bw.flush();
			System.out.println("presi fin ora "+ entroi160 +" su "+counter);

		}


		System.out.println("entro i 100km ne becco: " + entroi160);
		Double media= distances.stream().mapToDouble((x)->x).average().getAsDouble();
		System.out.println("la media e "+ media);


		bw.close();


	}	

}
