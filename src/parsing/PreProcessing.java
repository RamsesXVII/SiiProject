package parsing;

public class PreProcessing {
	public static void main( String[] args ) throws Exception {
		
		//cat training_set_tweets.txt | egrep '^[0-9]+[^\\S]' > training_set_tweets_fixed.txt
		String rawTweets= "resources/training_set_tweets_fixed.txt";
		String tweetsParsed ="resources/tweet_parsati.txt";
		
		Parser p = new Parser();
		
		p.tweetsParsing(rawTweets, tweetsParsed);
		
		//cut -f 2 training_set_users.txt | sort  | uniq -c | sed "s/^[ \t]*//" > occur_city.txt
		String rawUser2City = "resources/training_set_users.txt";
		String occurCity = "resources/occur_city.txt";
		String user2cityCleaned = "resources/user2city.txt";
		
		User2CityCleaner u2c = new User2CityCleaner();
		
		u2c.userCleaning(rawUser2City, user2cityCleaned, occurCity);
		
		City2Text c2t = new City2Text();
		
		String city2text = "resources/city2text.txt";
		c2t.userJoinCity(user2cityCleaned, city2text, tweetsParsed);	
	}
}
