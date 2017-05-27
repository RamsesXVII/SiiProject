package tfIdfUtility;

public class WordToCount {
	private String word;
	private Integer count;
	
	
	public WordToCount(String word, Integer count){
		this.word=word;
		this.count=count;
	}
	
	
	public String getWord() {
		return word;
	}
	
	public void increment(){
		this.count+=1;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getCount() {
		return count;
	}
	public void setWord(Integer count) {
		this.count = count;
	}
	
	@Override
	public boolean equals(Object o){
		WordToCount other= (WordToCount) o;
		return this.word.equals(other.getWord());
		
	}
	
	@Override
	public int hashCode(){
		return this.word.hashCode();
	}
	
	@Override
	public String toString(){
		return this.word+" "+this.count;
	}

}
