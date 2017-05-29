package tfIdfUtility;

import java.sql.SQLException;
import java.util.HashSet;

public class mainTest {
	public static void main(String[]args) throws ClassNotFoundException, SQLException{
		MySQLAccess maccess= new MySQLAccess();
		System.out.println("##############################");
		HashSet<String> uniqWords=new HashSet<String>();
		maccess.addUniqueWordsFromDB(uniqWords);
		System.out.println("##############################");
		System.out.println(uniqWords.size());
	}
}
