package validation;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		
		ValidationUserBased validami= new ValidationUserBased();
		validami.writeBestCity("resources/accuratezzaComp.txt");
		
	}

}
