package parsing;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class User2CityCleaner {
	public static void main(String[] args) throws IOException{

		//cut -f 2 training_set_users.txt | sort  | uniq -c | sed "s/^[ \t]*//" > occur_city.txt
		
		FileReader input = new FileReader("resources/training_set_users.txt");
		BufferedReader lines = new BufferedReader(input);
		
		CityAndStateParser parser = new CityAndStateParser("resources/occur_city.txt");

		File output = new File("resources/user2city.txt");
		FileWriter fw1 = new FileWriter(output,true);
		BufferedWriter bw1 = new BufferedWriter(fw1);


		String currentLine=lines.readLine();
		do{
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
			currentLine=lines.readLine();
		}while(currentLine!=null);

		bw1.flush();
		bw1.close();
		lines.close();


	}


}
