package validationAggregated;

import java.sql.SQLException;
import java.util.List;

import validation.City2Score;
import validation.CityOfTweetsExpert;

public class provaECommercialeTest {
	
	public static void main(String[]args) throws ClassNotFoundException, SQLException{
		
		CityOfTweetsExpert esperto= new CityOfTweetsExpert(5);
		List<City2Score> guessTweetPositionList = esperto.guessTweetPositionList("&& &&");
		System.out.println(guessTweetPositionList);
		
		
	}

	
}
