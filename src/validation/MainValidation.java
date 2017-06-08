package validation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

public class MainValidation {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		int i=3;
		if(args!=null){
			i= new Integer(args[0]);
		}
	
		
		ValidationUserBased validami= new ValidationUserBased(i);
		validami.writeBestCity("resources/K4BestWord.txt");
		
	}

}
 