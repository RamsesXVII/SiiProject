package UtilitiesTest;

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


import java.util.Map;

import validation.City2Score;


public class MySQLAccessTest {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public MySQLAccessTest() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/sii?user=root&password=root");
		statement = connect.createStatement();
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
	
	public Map<String, Map<String, Double>> populateWordMap() throws SQLException{
		Map<String,Map<String,Double>> wordMap= new HashMap<String, Map<String,Double>>();
		preparedStatement = connect.prepareStatement("select word,city,state,tfidf from testTable");
		resultSet = preparedStatement.executeQuery();
		String prevWord="";
		String currentWord="";
		String city=null;
		Double tfidf=null;
		Map<String, Double> oldCity2score=null;
		Map<String, Double>  city2score=null;
		while (resultSet.next()) {
			try{
				city=resultSet.getString("city")+","+resultSet.getString("state");
				tfidf=resultSet.getDouble("tfidf");
				currentWord= resultSet.getString("word");

				if(currentWord.equals(prevWord)){
					city2score.put(city,tfidf);
				}else{
					prevWord=currentWord;
					city2score= new HashMap<String, Double>();
					city2score.put(city,tfidf);
					oldCity2score=wordMap.put(currentWord, city2score);
					if(oldCity2score!=null){
						city2score.putAll(oldCity2score);
					}
				}
			}catch(Exception e){
				System.out.println(resultSet.getString("city")+","+resultSet.getString("state"));
				System.out.println(resultSet.getString("tfidf"));
			}
		}
		this.close();
		return wordMap;

	}


	public void insertNewRecord(String query) throws SQLException {
		preparedStatement = connect.prepareStatement(query);
		preparedStatement.executeUpdate();
	}
}