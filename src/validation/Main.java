package validation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
		CityOfTweetsTeller teller= new CityOfTweetsTeller();
		String separator="\\|EndOfCityID\\|   ";

		File out = new File("resources/distAvg.txt");
		FileWriter fw1 = new FileWriter(out,true);
		BufferedWriter bw1 = new BufferedWriter(fw1);
		BufferedReader br = new BufferedReader(new FileReader("resources/position2Tweet.txt"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] splitted = line.split(separator);
			String tweet = splitted[1];
			double[] testPosition=getPosition(splitted[0]);
			writeBestGuessed(teller,tweet,testPosition,bw1);
		}

		bw1.close();
		br.close();


	}
	//le stampe andranno rimosse dopo che avremo debbugato
	public static void writeBestGuessed(CityOfTweetsTeller teller,String tweet,double[] tweetPos, BufferedWriter bw)
			throws ClassNotFoundException, SQLException, IOException{

		List<City2Score> best3Cities= new LinkedList<City2Score>();
		best3Cities=teller.guessTweetPositionList(tweet);
		Map<String, Map<String, Double>> myWordMap = teller.getMyWordMap();
		//System.out.println("tweet corrente: "+tweet);
		bw.write(tweet+"\n");
		bw.flush();
		if(best3Cities!=null){
			for(City2Score city: best3Cities){

				double distance = 40000;	//defualt distance 
				double[] guessedPos=city.findCoordinate();
				if(guessedPos!=null){
					distance=distance(tweetPos[0],tweetPos[1], guessedPos[0], guessedPos[1],"K");
					String output = "testP: "+tweetPos[0]+","+tweetPos[1]+" guessP: "
							+ guessedPos[0]+","+guessedPos[1]+" distance: "+distance;
					bw.write(output+"\n");
					bw.flush();
				}
				//System.out.println(i+" "+city.toString()+":: ");
				for(String word: tweet.split(" ")){
					if(myWordMap.containsKey(word)){
						Double scoreWordCity = myWordMap.get(word).get(city.getCity());
						//System.out.print(word+ ":"+double1+" ");
					}
					//else{System.out.print(word+ ":nullParola ");}
				}
				//System.out.println("\n distanza:"+distance+"\n");
			}
			//System.out.println("");
		}
		bw.write("\n");
		bw.flush();
	}



	public static double distance(
			double lat1, double lng1, double lat2, double lng2,String km) {
		int r = 6371; // average radius of the earth in km
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = r * c;
		return d;
	}


	private static double[] getPosition(String position) {
		position=position.replace("UT: ", "");
		String[] split = position.split(",");
		double d1 = new Double(split[0]);
		double d2 = new Double(split[1]);
		double[] d1d= {d1,d2};
		return d1d;
	}


}
