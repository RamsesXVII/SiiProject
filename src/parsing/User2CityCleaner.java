package parsing;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class User2CityCleaner {
	
	public User2CityCleaner(){}
	
	/**
	 * @param rawUser2City file della forma UserId \tab Città
	 * @param fileOutput file della forma UserID |EndOfUserID| Città migliorato
	 * @param occurCity file con occorrenze delle città creato dalla query bash in preProcessing
	 * @throws IOException
	 */
	public void userCleaning(String rawUser2City, String fileOutput, String occurCity) throws IOException{
		
		FileReader input = new FileReader(rawUser2City);
		BufferedReader lines = new BufferedReader(input);
		
		CityAndStateParser parser = new CityAndStateParser(occurCity);

		File output = new File(fileOutput);
		FileWriter fw1 = new FileWriter(output,true);
		BufferedWriter bw1 = new BufferedWriter(fw1);


		String currentLine= null;
		while((currentLine = lines.readLine())!=null) {
			String[] splittedRow = currentLine.split("\t");
			String idUser= splittedRow[0];
			String location= splittedRow[1];
			String[] splitLocation = location.split("\\,");


			if(splitLocation.length==1){
				String state = parser.bestState4UntaggedCity(splitLocation[0]);
				if(state.length()!=2){ state= parser.bestAbbreviaton4State(state);}
				location= location+", "+state;
			}
			else if(splitLocation[1].replaceAll("^ *","").length()>3){
				location= splitLocation[0] +", "+parser.bestAbbreviaton4State(splitLocation[1].replaceAll("^ *",""));
			}

			bw1.write(idUser+"|EndOfUserID|"+location+"\n");
		}

		bw1.flush();
		bw1.close();
		lines.close();
	}
}