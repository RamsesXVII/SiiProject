package validation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

public class ValidationTweetBased extends Validation{
	private HashSet<String> userAnalized;


	public ValidationTweetBased() throws ClassNotFoundException, SQLException{
		super();
		userAnalized= new HashSet<String>();
	}

	public ValidationTweetBased(int k) throws ClassNotFoundException, SQLException {
		super(k);
		userAnalized= new HashSet<String>();
	}

	@Override
	public void writeBestCity(String outPath) throws ClassNotFoundException, SQLException, IOException{

		File out = new File(outPath);
		FileWriter fw1 = new FileWriter(out,true);
		BufferedWriter bw1 = new BufferedWriter(fw1);
		BufferedReader br = new BufferedReader(new FileReader("resources/test_set_tweet_parsati_fixed.txt"));
		User2tweetBuilder user2TweetB= new  User2tweetBuilder(0);
		Map<String, double[]> user2City = user2TweetB.getUser2City();
		Map<String, Integer> user2TweetsCount = user2TweetB.getUser2TweetsCount();
		double accuracyCurr=0;
		String line;
		int counter=0;
		
		while ((line = br.readLine()) != null) {
			try{
				String[] splitted = line.split("\\|EndOfUserID\\|");
				String tweet = splitted[1];
				String user= splitted[0];
				user=user.replaceAll("\\s+", "");
				//bw1.write("tweet corrente: "+tweet+"\n");
				
				if(!this.userAnalized.contains(user)&&counter!=0){
					userAnalized.add(user);
					System.out.println("accuracy: "+accuracyCurr);
					System.out.println("a meno di 160km: "+entroi160+" su "+counter+"\n");
					System.out.println("UserCorrente: "+user);
					bw1.write("accuracy: "+accuracyCurr+"\n");
					bw1.write("a meno di 160km: "+entroi160+" su "+counter+"\n\n");
					bw1.write("UserCorrente: "+user+"\n");
					bw1.flush();
					accuracy=0;
				}
				
				if(user2City.containsKey(user)&&user2TweetsCount.containsKey(user)){
					counter++;
					double[] testPosition =user2City.get(user);
					
					super.writeBestGuessed(tweet,testPosition,bw1);
					
					int numbTweet=user2TweetsCount.get(user);
					accuracyCurr= accuracy/new Double(numbTweet) ;
					//System.out.println("a meno di 160km: "+entroi160+" su "+counter);
					//bw1.write(output);
					//bw1.flush();
				}}catch(Exception e){
					e.printStackTrace();
				}
		}


		System.out.println("entro i 160km ne becco: " +entroi160+" su "+counter);
		Double media= distances.stream().mapToDouble((x)->x).average().getAsDouble();
		System.out.println("la media e "+ media);
		
		bw1.close();
		br.close();


	}



}
