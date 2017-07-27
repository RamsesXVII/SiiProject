package twitter;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Configuration {
	
	// Configuro le instanze per l'uso delle API di twitter4j
	
	private ConfigurationBuilder config = new ConfigurationBuilder().setOAuthConsumerKey("HMWZfB2f5j8gCJofhOHFh7MoY").setOAuthConsumerSecret("K6zVFBQVki7X8XgqjdotjNB6Y7tB4ojPJ0W1xXEbw17mJDLs6X").setOAuthAccessToken("3028071591-OxYNqRcfBFSRnWgugtPvBZnUWxfXkgNcX2Z1LsS").setOAuthAccessTokenSecret("P37wKFsdSWgDBxyHXZhUgaVL0T4g6mFieaJf08RotZWQ9");
	
 	TwitterStreamFactory tfs = new TwitterStreamFactory(config.build());
	TwitterStream twitterStream = tfs.getInstance();
	
	
	//TwitterFactory tf = new TwitterFactory(config.build());
	//Twitter twitter = tf.getInstance();
	
	public TwitterStream getTwitterStream() {
		return this.twitterStream;
	}
	//public Twitter getTwitter() {
	//	return this.twitter;
	//}
}