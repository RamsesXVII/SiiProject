package twitter;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Retrieve {
	public static void main(String[] args)  throws TwitterException{
		Configuration config = new Configuration();
		Twitter twitter = (Twitter) config.getTwitterStream();
		Query q1 = new Query("#sun");

		try{
			QueryResult result = twitter.search(q1);
			for (Status tweet : result.getTweets()) {
				System.out.println("text : "+ tweet.getGeoLocation() +" "+ tweet.getText());
				for (int i=0; i!=tweet.getHashtagEntities().length; i++){
					System.out.print(tweet.getHashtagEntities()[i]);
					System.out.println();
				}
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
	  
	}

}
