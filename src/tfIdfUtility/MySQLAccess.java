package tfIdfUtility;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
//create database sii
//#CREATE TABLE wordToCitytfIdf (word char(100) NOT NULL,  city char(100) NOT NULL, tfidf decimal (20,19));


public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public MySQLAccess() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver

		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/sii?user=root&password=root");
		statement = connect.createStatement();

	}


	/**
	 * persist tfIdf values
	 * @throws SQLException 
	 */

	public void persistfIdfFromMap1(String word, HashMap<String,Double> citiesToScore ) throws SQLException{
		String query="INSERT INTO sii.wordToCitytfIdf (word,city,tfIdf) VALUES ";
		for(String city:citiesToScore.keySet())
			query+="('"+word+"','"+city+"','"+citiesToScore.get(city)+"'), ";

		query=query.substring(0,query.length()-2);
		query+=";";
		preparedStatement = connect.prepareStatement(query);
		preparedStatement.executeUpdate();

	}

	public void addUniqueWordsFromDB(HashSet<String> uniqWords) throws SQLException {
		preparedStatement = connect.prepareStatement("select distinct word from wordtocitytfidf;");
		resultSet = preparedStatement.executeQuery();
		writeWordsResult(resultSet,uniqWords);

	}

	private void writeWordsResult(ResultSet resultSet, HashSet<String> uniqWords) throws SQLException {
		while (resultSet.next()) {
			uniqWords.add(resultSet.getString("word"));
		}
	}

	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}