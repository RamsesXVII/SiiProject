package twitter;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.common.base.CharMatcher;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;


class StreamTwitter {
	public static void main(String[] args) throws TwitterException, IOException{
		PrintWriter writer = new PrintWriter("datasetFromStream/datasetStreaming.txt", "UTF-8");


		CityUtility cUtility= new CityUtility();
		Configuration config = new Configuration();
		TwitterStream twitter = config.getTwitterStream();
		StatusListener listener = new StatusListener(){
			public void onStatus(Status status) {
				String location=status.getUser().getLocation();
				boolean isAsciiLocation =false;
				if(location!=null){
					isAsciiLocation=CharMatcher.ASCII.matchesAllOf(location);

					if(isAsciiLocation && cUtility.isUSALocation(location)&&status.getGeoLocation()!=null ){
						writer.println(status.getUser().getLocation());
						writer.println(status.getUser().getName() + " : " + status.getText().replaceAll("[^\\x00-\\x7F]", ""));
						writer.println("GEOLOCATION" + " : " + status.getGeoLocation());
						writer.println("EOT");
					}
				}
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
			public void onScrubGeo(long arg0, long arg1) {			
			}
			public void onStallWarning(StallWarning arg0) {			
			}
		};
		//TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitter.addListener(listener);
		//FilterQuery q1 = new FilterQuery("#oscar");
		// sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
		twitter.sample();
		//twitter.filter("#romainter");
		writer.close();
	}


}
