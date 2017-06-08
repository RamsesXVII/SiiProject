package validation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		
	
		
		ValidationUserBased validami= new ValidationUserBased();
		validami.writeBestCity("resources/K5BestWord.txt");
		
	}

}
 