package parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Parser {
	
	public Parser(){}
	
	
	/**
	 * @param rawTweets file dei tweets fixati con la query bash in preProcessing.
	 * Devono iniziare con lo userID.
	 * @param fileOutput tweets puliti da parole lunghe 1, emoticon, link ...
	 * @throws IOException
	 */
	public void tweetsParsing(String rawTweets, String fileOutput) throws IOException {
		
		FileReader input = new FileReader(rawTweets);
		BufferedReader bufRead = new BufferedReader(input);

		File out1 = new File(fileOutput);
		FileWriter fw1 = new FileWriter(out1,true);
		BufferedWriter bw1 = new BufferedWriter(fw1);

		ParserUtility pUtility= new ParserUtility();

		String linea = null;

		//effettua il parsing di tutti i tweet del file
		while ((linea = bufRead.readLine())!=null){
			
			String cleanedLine = pUtility.cleanSentence(linea);
			if (cleanedLine!= null){
				bw1.write(cleanedLine);
				bw1.write("\n");
				bw1.flush();		
			}		
		}

		bw1.close();
		bufRead.close();	
	}
}
