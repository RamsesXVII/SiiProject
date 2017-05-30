package tfIdfUtility;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
//create database sii
//#CREATE TABLE wordToCityCount (     word char(100) NOT NULL,  city char(100) NOT NULL,  count int NOT NULL, PRIMARY KEY (word,city) ) ENGINE=MyISAM;


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
	 * Add a new record to the db
	 * @param word
	 * @param city
	 * @param count
	 * @throws Exception
	 */
	public void addRecord(String word,String city, Integer count) throws Exception {

		preparedStatement = connect.prepareStatement("INSERT INTO sii.wordToCityCount (word,city,count) VALUES ('"+word+"','"+city+"','"+count+"')");
		preparedStatement.executeUpdate();

	}

	public List<String> getAllWords() throws SQLException{
		preparedStatement = connect.prepareStatement("select distinct word from wordToCityCount;");
		resultSet = preparedStatement.executeQuery();
		return writeWordsSet(resultSet);
	}

	//TODO renderli tutti parametrici (con input)
	public List<CityToCount> getCitiesFromWord(String word) throws SQLException{
		preparedStatement = connect.prepareStatement("select city, count from wordToCityCount where word='"+word+"';");
		resultSet = preparedStatement.executeQuery();
		return writeCitiesSet(resultSet);
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

	private List<CityToCount> writeCitiesSet(ResultSet resultSet) throws SQLException {
		List<CityToCount>citiesToCount=new LinkedList<CityToCount>();

		while (resultSet.next()) {
			citiesToCount.add(new CityToCount(resultSet.getString("city"),new  Integer(resultSet.getString("count"))));
		}

		return citiesToCount;
	}

	private List<String> writeWordsSet(ResultSet resultSet) throws SQLException {
		List<String>words=new LinkedList<String>();

		while (resultSet.next()) {
			words.add(resultSet.getString("word"));
		}

		return words;
	}

	public void peristIdf(String word, String city, double tfIdf) throws SQLException {
		preparedStatement = connect.prepareStatement("INSERT INTO sii.wordToCitytfIdf (word,city,tfIdf) VALUES ('"+word+"','"+city+"','"+tfIdf+"')");
		preparedStatement.executeUpdate();

	}

	/**
	 * persist tfIdf values
	 * @throws SQLException 
	 */

	public void persistfIdfFromMap(HashMap<String,HashMap<String,Double>>word2cityTfIdf) throws SQLException{
		for(String word: word2cityTfIdf.keySet()){
			HashMap<String,Double> citiesToTfIdf=word2cityTfIdf.get(word);
			for(String city:citiesToTfIdf.keySet()){
				Double tfIdf=citiesToTfIdf.get(city);
				//System.out.println(tfIdf);
				preparedStatement = connect.prepareStatement("INSERT INTO sii.wordToCitytfIdf (word,city,tfIdf) VALUES ('"+word+"','"+city+"','"+tfIdf+"')");
				preparedStatement.executeUpdate();

			}
		}
	}
	public void persistfIdfFromMap1(String word, HashMap<String,Double> citiesToScore ) throws SQLException{
		String query="INSERT INTO sii.wordToCitytfIdf (word,city,tfIdf) VALUES ";
		for(String city:citiesToScore.keySet())
			query+="('"+word+"','"+city+"','"+citiesToScore.get(city)+"'), ";

		query=query.substring(0,query.length()-2);
		query+=";";
		//sSystem.out.println(query);
		preparedStatement = connect.prepareStatement(query);
		preparedStatement.executeUpdate();

//		INSERT INTO sii.wordToCitytfIdf (word,city,tfIdf) VALUES (roma,ao3),(milano,bella,4);

	}

	public void addUniqueWordsFromDB(HashSet<String> uniqWords) throws SQLException {
		preparedStatement = connect.prepareStatement("select distinct word from wordtocitytfidf;");
		resultSet = preparedStatement.executeQuery();
		writeWordsResult(resultSet,uniqWords);
		
	}

	private void writeWordsResult(ResultSet resultSet, HashSet<String> uniqWords) throws SQLException {
		// TODO Auto-generated method stub
		while (resultSet.next()) {
			uniqWords.add(resultSet.getString("word"));
		}

	}
}