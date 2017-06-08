package validation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ValidationUserBased extends Validation{

	public ValidationUserBased(){
		super();
	}

	public ValidationUserBased(int k){
		super(k);
	}


	@Override
	public void writeBestCity(String outPath) throws IOException, ClassNotFoundException, SQLException{

		File out = new File(outPath);
		FileWriter fw1 = new FileWriter(out,true);
		BufferedWriter bw = new BufferedWriter(fw1);

		CityOfTweetsExpert teller= new CityOfTweetsExpert(K);
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
			
			super.writeBestGuessed(teller, longTweet, tweetPos, bw);
			
			String output = "a meno di 160km: "+ entroi160 +" su "+ counter;
			bw.write(output+"\n\n");
			bw.flush();
			System.out.println(output+"\n");

		}


		System.out.println("entro i 100km ne becco: " + entroi160);
		Double media= distances.stream().mapToDouble((x)->x).average().getAsDouble();
		System.out.println("la media e "+ media);


		bw.close();


	}	

}