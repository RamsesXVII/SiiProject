package parsing;

public class PreProcessing {
	public static void main( String[] args ) throws Exception {
		
		//CREA TWEET FIXED
		//cat training_set_tweets.txt | egrep --text '^[0-9]+[^\\S]' > training_set_tweets_fixed.txt
		
		String rawTweets= "resources/training_set_tweets_fixed.txt";
		String tweetsParsed ="resources/tweet_parsati.txt";
		
		Parser p = new Parser();
		
		p.tweetsParsing(rawTweets, tweetsParsed);
		
		//CREA OCCUR CITY
		//cut -f 2 training_set_users.txt | sort  | uniq -c | sed "s/^[ \t]*//" > occur_city.txt
		
		String rawUser2City = "resources/training_set_users.txt";
		String occurCity = "resources/occur_city.txt";
		String user2cityCleaned = "resources/user2city.txt";
		
		User2CityCleaner u2c = new User2CityCleaner();
		
		u2c.userCleaning(rawUser2City, user2cityCleaned, occurCity);
		
		Location2Text c2t = new Location2Text();
		
		String city2text = "resources/city2text.txt";
		c2t.userJoinLocation(user2cityCleaned, city2text, tweetsParsed);
		
		//cat test_set_tweets.txt | egrep --text '^[0-9]+[^\\S]'> test_set_tweets_clean.txt
		//con sublime aggiungi uendofuser al posto della tav in testsetuser
		String testSet= "resources/test_set_tweets_clean.txt";
		String testSetParsato ="resources/test_set_tweets_parsati.txt";
		String usersSetTest = "resources/test_set_users.txt";
		String position2Tweet = "resources/position2Tweet.txt";
		
		p.tweetsParsing(testSet, testSetParsato);
		c2t.userJoinLocation(usersSetTest,position2Tweet, testSetParsato);
		
		
	}
}
