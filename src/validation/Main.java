package validation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
		CityOfTweetsTeller teller= new CityOfTweetsTeller();
		String separator="\\|EndOfCityID\\|   ";

		File out = new File("resources/distAvg.txt");
		FileWriter fw1 = new FileWriter(out,true);
		BufferedWriter bw1 = new BufferedWriter(fw1);
		BufferedReader br = new BufferedReader(new FileReader("resources/position2Tweet.txt"));
		String line;
		int i=0;
		while ((line = br.readLine()) != null) {

			String[] splitted = line.split(separator);
			String string = splitted[1];
			double[] tweetPos=getPosition(splitted[0]);
			double[] guessedPos = teller.guessTweetPosition(string);
			double distance=distance(tweetPos[0],tweetPos[1], guessedPos[0], guessedPos[1]);
			String output = "test Position:("+tweetPos[0]+","+tweetPos[1]+") guessed Position: ("
					+ guessedPos[0]+","+guessedPos[1]+") distance: "+distance;
			System.out.println(output);
			bw1.write(output);
			bw1.write("\n");
			bw1.flush();	
			if(i==100)
				break;
			i++;
			}

		bw1.close();
		br.close();


	}

	private static double distance(double lon1, double lat1, double lon2, double lat2) {
		int R = 6371; // km
		double x = (lon2 - lon1) * Math.cos((lat1 + lat2) / 2);
		double y = (lat2 - lat1);
		double distance = Math.sqrt(x * x + y * y) * R;
		return distance;
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
