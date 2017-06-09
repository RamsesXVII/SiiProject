package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class ParserUtility {
	private String[] notWords={"@","#","http:","ftp:","https:","mailto:",".com",".edu","www."};
	private HashSet<String> notWordsSet;
	//private HashSet<String> stopWordSet;

	public ParserUtility() throws IOException{
		this.notWordsSet=new HashSet<>();

		for(String word : notWords)
			notWordsSet.add(word);
		
		//this.stopWordSet = new HashSet<>();
		//
//		FileReader input = new FileReader("dictionary/stopWords.txt");
//		BufferedReader bufRead = new BufferedReader(input);
//		
//		String linea = null;
//		while ((linea = bufRead.readLine())!=null){
//			this.stopWordSet.add(linea);
//		}
//		
//		bufRead.close();
	}

	/**
	 * 
	 * @param sentence il tweet da parsare
	 * @return String, tweet contenente solo parole valide 
	 */
	public String cleanSentence(String sentence){

		String [] splittedString=sentence.split("\\s+");
		String cleanedID= splittedString[0] + " |EndOfUserID| ";
		String cleanedText= "";
		
		for(int i=2;i<splittedString.length-2;i++){
				boolean validWord=true;
				String word = splittedString[i].toLowerCase();

				for(String notWord: this.notWordsSet){
					if(word.contains(notWord))
						validWord= false;
				}

				if(validWord){
					word=word.replaceAll("[^A-Za-z0-9\\-\\&\\' ]", "");
					
					if(!((word.length()==1) || (word.matches("[\\-\\@\\&]+")))){
						if(word.contains("'"))
							word= word.replaceAll("'", "@");
						cleanedText= cleanedText + " " + word;
					}
				}
			}
		
		if (!(cleanedText.equals("")))
			 return cleanedID + cleanedText;
		return null;
	}
}
