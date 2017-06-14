package validation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

public class MainValidation {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		int i=5;
		int[] numb={3,4,1};

		if(args.length>0){
			i= new Integer(args[0]);
		}


		//ValidationTweetBased validami= new ValidationTweetBased(i);
		//validami.writeBestCity("resources/cancellami.txt");

		ValidationUserBased validami= new ValidationUserBased();
		for(int k: numb){
			validami.setK(k);
			validami.writeBestCity("resources/K"+k+"NDatabase.txt");
		}

	}

}