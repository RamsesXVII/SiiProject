package validationAggregated;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import twitter.CityUtility;
import validation.City2Score;
import validation.CityOfTweetsExpert;
import validation.Validation;


public class provaECommercialeTest {
	
	public static void main(String[]args) throws ClassNotFoundException, SQLException{
		
		CityOfTweetsExpert esperto= new CityOfTweetsExpert(5);
		List<City2Score> guessTweetPositionList = esperto.guessTweetPositionList("&& &&");
		System.out.println(guessTweetPositionList);
		
		
	}

	
}
