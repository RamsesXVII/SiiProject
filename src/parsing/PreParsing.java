package parsing;

public class PreParsing {
	//CREA TRAINING TWEETS FIXED
	//cat training_set_tweets.txt | grep -P --text '^[0-9]+\t[0-9]' > training_set_tweets_fixed.txt
	
	//CREA TEST TWEETS FIXED
	//cat test_set_tweets.txt | grep -P --text '^[0-9]+\t[0-9]' > test_set_tweets_fixed.txt
	
	public static void main( String[] args ) throws Exception {
		
		Parser p = new Parser();
		
		System.out.println("------ TRAINING SET ------");
		
		String rawTweets= "resources/training_set_tweets_fixed.txt";
		String tweetsParsed ="resources/training_set_tweet_parsati.txt";
		
		System.out.println("****** INIZIO PARSING ******");
		p.tweetsParsing(rawTweets, tweetsParsed);
				
		System.out.println("------ TEST SET ------");
		
		String testSet= "resources/test_set_tweets_fixed.txt";
		String testSetParsato ="resources/test_set_tweets_parsati.txt";
		
		System.out.println("****** INIZIO PARSING ******");
		p.tweetsParsing(testSet, testSetParsato);
	}
}