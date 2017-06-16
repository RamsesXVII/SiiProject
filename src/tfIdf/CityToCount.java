package tfIdf;

public class CityToCount {
	private String city;
	private Integer count;
	public String getCity() {
		return city;
	}
	
	public CityToCount(String city, Integer count){
		this.city=city;
		this.count=count;
	}
	
	public void increment(){
		this.count+=1;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	@Override
	public boolean equals(Object o){
		CityToCount other= (CityToCount) o;
		return this.city.equals(other.getCity());
		
	}
	
	@Override
	public int hashCode(){
		return this.city.hashCode();
	}
	
	@Override
	public String toString(){
		return this.city+" "+this.count;
	}

}
