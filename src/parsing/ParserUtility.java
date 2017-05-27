package parsing;

import java.util.HashSet;

public class ParserUtility {
	private String[] notWords={"@","#","http:","ftp:","https:","mailto:",".com",".edu","www."};
	private HashSet<String> notWordsSet;

	public ParserUtility(){
		this.notWordsSet=new HashSet<>();

		for(String word : notWords)
			notWordsSet.add(word);
	}

	/**
	 * 
	 * @param sentence il tweet da parsare
	 * @return String, tweet contenente solo parole valide 
	 */
	public String cleanSentence(String sentence){

		String [] splittedString=sentence.split("\\s+");
		String cleanedLine= splittedString[0] + " |EndOfUserID|";
		
		for(int i=2;i<splittedString.length;i++){

			if(!(i>splittedString.length-3)){
				boolean containsNotWords=false;

				for(String notWord:notWordsSet){
					if(splittedString[i].contains(notWord))
						containsNotWords=true;
				}

				if(!containsNotWords){
					splittedString[i]=splittedString[i].replaceAll("[^A-Za-z0-9\\-\\'\\& ]", "");
					cleanedLine= cleanedLine + " " + splittedString[i];
				}
			}
		}
		return cleanedLine;
	}
}
