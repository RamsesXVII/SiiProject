package parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CityList {
	
	public static void main(String[]args) throws IOException{
	
		FileReader input1 = new FileReader("resources/city2user.txt");
		BufferedReader city = new BufferedReader(input1);
		
		File out1 = new File("resources/city.txt");
		FileWriter fw1 = new FileWriter(out1,true);
		BufferedWriter bw1 = new BufferedWriter(fw1);
		
		String linea;
		String[] citta;
		
		while ((linea=city.readLine())!=null){
			citta=linea.split("  ");
			bw1.write(citta[0]+", ");
			bw1.flush();
		}
		city.close();
		bw1.close();
	}

}