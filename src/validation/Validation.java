package validation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Validation {

	protected int K;
	protected int entroi160;
	protected List<Double> distances;
	protected double accuracy = 0;
	final protected int toleranceDistance=160;

	//interfaccica
	public abstract void writeBestCity(String outPath) throws ClassNotFoundException, SQLException, IOException;

	public Validation(){
		this.K=5;
		this.distances=new LinkedList<Double>();
		this.entroi160=0;
	}


	public Validation(int k2) {
		this.K=k2;
		this.distances=new LinkedList<Double>();
		this.entroi160=0;
	}

	public void writeBestGuessed(CityOfTweetsExpert teller,String tweet,double[] tweetPos, BufferedWriter bw)
			throws ClassNotFoundException, SQLException, IOException{
		List<City2Score> best3Cities= new LinkedList<City2Score>();
		best3Cities=teller.guessTweetPositionList(tweet);
		//Map<String, Map<String, Double>> myWordMap = teller.getMyWordMap();

		int bb=0;
		if(best3Cities!=null){
			for(City2Score city: best3Cities){

				double[] guessedPos=city.findCoordinate();
				if(guessedPos!=null && tweetPos!=null){

					double distance=distance(tweetPos[0],tweetPos[1], guessedPos[0], guessedPos[1],"K");

					String output = "distance: "+distance;
					distances.add(distance);
					//bw.write(output+"\n");
					//bw.flush();
					//System.out.println(output+" citta: "+city.getCity()+" score: "+city.getScore());
					if(distance<toleranceDistance){
						System.out.println("utente coordinate: "+tweetPos[0]+","+tweetPos[1]);
						accuracy=accuracy+1.0;
						bb++;
						if(bb==1){
							entroi160++;
						}
					}
				}
				else{
					System.out.println("nessuna parola classificate nel tweet \n");
					//bw.write("nessuna parola classificate nel tweet \n");
					//bw.flush();
					break;
				}

				/*
				//System.out.println(i+" "+city.toString()+":: ");
				for(String word: tweet.split(" ")){
					if(myWordMap.containsKey(word)){
						Double scoreWordCity = myWordMap.get(word).get(city.getCity());
						//System.out.print(word+ ":"+double1+" ");
					}
					//else{System.out.print(word+ ":nullParola ");}
				}
				//System.out.println("\n distanza:"+distance+"\n");*/
			}

			//System.out.println("");
		}
	}




	public double distance(
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


	public double[] getPosition(String position) {
		position=position.replace("UT: ", "");
		String[] split = position.split(",");
		double d1 = new Double(split[0]);
		double d2 = new Double(split[1]);
		double[] d1d= {d1,d2};
		return d1d;
	}



}