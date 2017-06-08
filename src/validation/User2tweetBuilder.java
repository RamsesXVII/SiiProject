package validation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class User2tweetBuilder {

	private Map<String,String> user2tweet;
	private Map <String,Integer> user2TweetsCount;
	private Map<String,double[]> user2City;
	private String pathPosition2Tweet="resources/test_set_tweets_parsati.txt";
	private String pathUser2Position="resources/test_set_users.txt";




	public User2tweetBuilder() throws IOException{
		System.out.println("INIZIO ANALISI BASATA  SULL'UTENTE");
		this.user2tweet=new TreeMap<>();
		this.user2TweetsCount= new TreeMap<>();
		createUserCityMap();
	}

	public User2tweetBuilder(int i) throws IOException{
		System.out.println("INIZIO ANALISI BASATA SUL TWEET");
		this.user2tweet=new TreeMap<>();
		this.user2TweetsCount= new TreeMap<>();
		createUserCityMap();
		createUserTweetCount();

	}


	public User2tweetBuilder(String pathToTestFile) throws IOException {
		this.pathPosition2Tweet=pathToTestFile;
		this.user2tweet=new TreeMap<>();
		this.user2TweetsCount= new TreeMap<>();
		createUserCityMap();
	}

	private void createUserTweetCount()  throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(this.pathPosition2Tweet));
		String line;
		while ((line = br.readLine()) != null) {
			try{
				String[] splitted = line.split("\\|EndOfUserID\\|");
				String userID=splitted[0].replaceAll("\\s+", "");
				if(this.user2TweetsCount.containsKey(userID)){
					this.increment(userID);}
				else{
					this.user2TweetsCount.put(userID, 1);
				}
			}catch(Exception e){
				System.out.println("malformattato");
			}
		}
		br.close();

	}

	private void createUserCityMap() throws IOException {
		this.user2City=new TreeMap<String, double[]>();
		BufferedReader br = new BufferedReader(new FileReader(this.pathUser2Position));
		String line;



		while ((line = br.readLine()) != null) {
			try{
				String[] split = line.split("\\|EndOfUserID\\|UT:");
				String user = split[0];
				user=user.replaceAll("\\s+", "");
				String location = split[1];
				if(!this.user2City.containsKey(user)){
					String[] latlng = location.split(",");
					double[] d= new double[2];
					d[0]=new Double(latlng[0]);
					d[1]=new Double(latlng[1]);
					this.user2City.put(user, d);
					//this.user2TweetsCount2.put(user,1);
				}else{
					//this.increment2(user);
				}

			}catch(Exception e){
				System.out.println("malformattato");
			}
		}

		br.close();
	}

	public void computerUser2tweet() throws IOException{

		BufferedReader br = new BufferedReader(new FileReader(this.pathPosition2Tweet));
		String line;

		try{
			while ((line = br.readLine()) != null) {


				String[] splitted = line.split("\\|EndOfUserID\\|");
				String tweet = splitted[1];
				String userID=splitted[0].replaceAll("\\s+", "");
				if(this.user2tweet.containsKey(userID)){
					String tweetMerged=this.user2tweet.get(userID);
					tweetMerged+=" "+tweet;
					this.user2tweet.put(userID, tweetMerged);
					this.increment(userID);
				}
				else{
					this.user2TweetsCount.put(userID, 1);
					this.user2tweet.put(userID,tweet);	
				}
			}
		}catch(Exception e){
			System.out.println("malformattato");
		}
		br.close();
	}

	private void increment(String userID) {
		Integer currentCount=this.user2TweetsCount.get(userID);
		currentCount++;
		this.user2TweetsCount.put(userID, currentCount);

	}


	public String getPathPosition2Tweet() {
		return pathPosition2Tweet;
	}

	public void setPathPosition2Tweet(String pathPosition2Tweet) {
		this.pathPosition2Tweet = pathPosition2Tweet;
	}

	public Map<String, String> getUser2tweet() {
		return user2tweet;
	}

	public void setUser2tweet(Map<String, String> user2tweet) {
		this.user2tweet = user2tweet;
	}

	public Map<String, Integer> getUser2TweetsCount() {
		return user2TweetsCount;
	}


	public void setUser2TweetsCount(Map<String, Integer> user2TweetsCount) {
		this.user2TweetsCount = user2TweetsCount;
	}

	public Map<String, double[]> getUser2City() {
		return user2City;
	}

	public void setUser2City(Map<String, double[]> user2City) {
		this.user2City = user2City;
	}

}
