package validation;

import java.io.IOException;
import java.sql.SQLException;

public class MainValidation {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {

		int[] numb={5};


		ValidationUserBased validami= new ValidationUserBased();
		for(int k: numb){
			validami.setK(k);
			validami.writeBestCity("resources/K"+k+"NDatabase.txt");
		}

	}

}