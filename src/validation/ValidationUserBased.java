package validation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ValidationUserBased extends Validation{

	public ValidationUserBased() throws ClassNotFoundException, SQLException{
		super();
	}

	public ValidationUserBased(int k) throws ClassNotFoundException, SQLException{
		super(k);
	}


	@Override
	public void writeBestCity(String outPath) throws IOException, ClassNotFoundException, SQLException{

		File out = new File(outPath);
		FileWriter fw1 = new FileWriter(out,true);
		BufferedWriter bw = new BufferedWriter(fw1);

		User2tweetBuilder user2TweetB= new  User2tweetBuilder();
		Map<String, double[]> user2City = user2TweetB.getUser2City();
		user2TweetB.computerUser2tweet();
		
		Map<String, String> user2tweet = user2TweetB.getUser2tweet();
		int counter=0;
		for(String user: user2tweet.keySet()){
			counter++;
			System.out.println("UserCorrente: "+user);
			bw.write("UserCorrente: "+user);
			String longTweet=user2tweet.get(user);
			double[] tweetPos=user2City.get(user);
			
			super.writeBestGuessed(longTweet, tweetPos, bw);
			
			String output = "a meno di 160km: "+ entroi160 +" su "+ counter;
			bw.write(output+"\n\n");
			bw.flush();
			System.out.println(output+"\n");

		}


		Double media= distances.stream().mapToDouble((x)->x).average().getAsDouble();
		String distanceAvg = "la distanza media "+ media;
		String finalOut = "entro i 160km: " + entroi160+" su "+counter;
		String finalAccuracy = "accuratezza totale: " + (accuracy/new Double(counter));
		System.out.println(finalOut);
		System.out.println(distanceAvg);
		System.out.println(finalAccuracy);
		
		bw.write(finalOut+"\n"+distanceAvg+"\n"+finalAccuracy);
		bw.flush();
		bw.close();


	}	

}