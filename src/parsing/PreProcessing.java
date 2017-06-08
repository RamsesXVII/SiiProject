package parsing;

public class PreProcessing {
	//CREA TRAINING TWEETS FIXED
	//cat training_set_tweets.txt | grep -P --text '^[0-9]+\t[0-9]' > training_set_tweets_fixed.txt
	
	//CREA OCCUR CITY
	//cut -f 2 training_set_users.txt | sort  | uniq -c | sed "s/^[ \t]*//" > occur_city.txt
	
	//CREA TEST TWEETS FIXED
	//cat test_set_tweets.txt | grep -P --text '^[0-9]+\t[0-9]' > test_set_tweets_fixed.txt
	
	//MODIFICA TEST SET USER
	//con sublime aggiungi |EndOfUserID| al posto della tab in test_set_user
	
	public static void main( String[] args ) throws Exception {
		
		System.out.println("------ TRAINING SET ------");
		
		String rawTweets= "resources/training_set_tweets_fixed.txt";
		String tweetsParsed ="resources/tweet_parsati.txt";
		
		Parser p = new Parser();
		
		System.out.println("****** INIZIO PARSING ******");
		p.tweetsParsing(rawTweets, tweetsParsed);
		
		String rawUser2City = "resources/training_set_users.txt";
		String occurCity = "resources/occur_city.txt";
		String user2cityCleaned = "resources/user2city.txt";
		
		User2CityCleaner u2c = new User2CityCleaner();
		
		System.out.println("****** INIZIO CLEANER ******");
		u2c.userCleaning(rawUser2City, user2cityCleaned, occurCity);
		
		String city2text = "resources/city2text.txt";
		Location2Text c2t = new Location2Text();
		
		System.out.println("****** INIZIO JOIN ******");
		c2t.userJoinLocation(user2cityCleaned, city2text, tweetsParsed);
				
		System.out.println("------ TEST SET ------");
		
		String testSet= "resources/test_set_tweets_fixed.txt";
		String testSetParsato ="resources/test_set_tweets_parsati.txt";
		String usersSetTest = "resources/test_set_users.txt";
		String position2Tweet = "resources/position2Tweet.txt";
		
		System.out.println("****** INIZIO PARSER ******");
		p.tweetsParsing(testSet, testSetParsato);
		
		System.out.println("****** INIZIO JOIN ******");
		c2t.userJoinLocation(usersSetTest,position2Tweet, testSetParsato);
	}
}