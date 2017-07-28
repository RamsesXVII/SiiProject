package utility;

import info.debatty.java.stringsimilarity.Jaccard;
import java.util.LinkedList;


public class JaccardCoefficient {
	
	private Jaccard ja=new Jaccard(2);
	private LinkedList<String> dictionary;

	public JaccardCoefficient(){
		this.ja = new Jaccard(2);
	}
	
	public JaccardCoefficient(LinkedList<String> dict){
		this.ja= new Jaccard(2);
		this.dictionary = dict;
	}
		
	public String getWord(String word){

			double valore = 0.0;
			boolean trovato=false;
			String parola=null;
			
			int i = 0;
			while ( i < this.dictionary.size() && (!trovato)){
				
					String dic = this.dictionary.get(i);
					
					if((ja.similarity(dic, word))>valore){
						parola=dic;
						valore=ja.similarity(dic, word);
					}else if(ja.similarity(dic, word)==1.0){
						parola=dic;
						trovato=true;
					}
					i++;
				}
		return parola;
	}
}