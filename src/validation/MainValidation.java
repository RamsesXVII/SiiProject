package validation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

public class MainValidation {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		int i=5;
		
		if(args.length>0){
			i= new Integer(args[0]);
		}
	
		
		//ValidationTweetBased validami= new ValidationTweetBased(i);
		//validami.writeBestCity("resources/cancellami.txt");
			
		ValidationUserBased validami= new ValidationUserBased(i);
		validami.writeBestCity("resources/K5NDatabase.txt");
		
	}

}