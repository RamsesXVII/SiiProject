package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;


public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String tableName="wordStopped";

	
	
	public MySQLAccess() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver

		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/sii?user=root&password=root");
		statement = connect.createStatement();
	}
	
	private int getLattice(String city){
		String[] latLong = city.split("\\$");
		int latitude = Integer.parseInt(latLong[0].split("\\.")[0]);
		int longitude = Integer.parseInt(latLong[1].split("\\.")[0]);
		
		int lattice = (latitude * 10000) + (longitude * -1);
		
		return lattice;
	}
	
	public Map<String, Map<Integer, Double>> populateWordtoLatticeMap() throws SQLException{
		resultSet.beforeFirst();
		Map<String,Map<Integer,Double>> wordLatticeMap= new TreeMap<String, Map<Integer,Double>>();
//		preparedStatement = connect.prepareStatement("select word,city,tfidf from "+tableName+" order by word");
//		resultSet = preparedStatement.executeQuery();
		String prevWord="";
		String currentWord="";
		String city=null;
		Double tfidf=null;
		Map<Integer, Double> oldLattice2score=null;
		Map<Integer, Double>  lattice2score=null;
		System.out.println("fine query: inizio ad aggiungere alla mappa");
		while (resultSet.next()) {
			try{
				city=resultSet.getString("city"); 
				tfidf=resultSet.getDouble("tfidf");
				currentWord= resultSet.getString("word");

				if(currentWord.equals(prevWord)){
					int lattice = this.getLattice(city);
					Double oldTfidf = lattice2score.put(lattice,tfidf);
					if (oldTfidf!=null)
						lattice2score.put(lattice, oldTfidf + tfidf);
				}else{
					prevWord=currentWord;
					lattice2score= new TreeMap<Integer, Double>();
					lattice2score.put(this.getLattice(city),tfidf);
					oldLattice2score= wordLatticeMap.put(currentWord, lattice2score);
					if(oldLattice2score!=null){
						lattice2score.putAll(oldLattice2score);
					}
				}
			}catch(Exception e){
				System.out.println(resultSet.getString("city"));
				System.out.println(resultSet.getString("tfidf"));
			}
		}
		this.close();
		System.out.println("ho finito di aggiungere in mappa");
		return wordLatticeMap;
	}
	
	public Map<String, Map<String, Double>> populateWordMap() throws SQLException{
		Map<String,Map<String,Double>> wordMap= new TreeMap<String, Map<String,Double>>();
		preparedStatement = connect.prepareStatement("select word,city,tfidf from "+tableName+" order by word");
		resultSet = preparedStatement.executeQuery();
		String prevWord="";
		String currentWord="";
		String city=null;
		Double tfidf=null;
		Map<String, Double> oldCity2score=null;
		Map<String, Double>  city2score=null;
		System.out.println("fine query: inizio ad aggiungere alla mappa");
		while (resultSet.next()) {
			try{
				city=resultSet.getString("city"); 
				tfidf=resultSet.getDouble("tfidf");
				currentWord= resultSet.getString("word");

				if(currentWord.equals(prevWord)){
					city2score.put(city,tfidf);
				}else{
					prevWord=currentWord;
					city2score= new TreeMap<String, Double>();
					city2score.put(city,tfidf);
					oldCity2score=wordMap.put(currentWord, city2score);
					if(oldCity2score!=null){
						city2score.putAll(oldCity2score);
					}
				}
			}catch(Exception e){
				System.out.println(resultSet.getString("city"));
				System.out.println(resultSet.getString("tfidf"));
			}
		}
		
		System.out.println("ho finito di aggiungere in mappa");
		return wordMap;

	}




	/**
	 * persist tfIdf values
	 * @throws SQLException 
	 */

	public void persistfIdfFromMap1(String word, HashMap<String,Double> citiesToScore ) throws SQLException{

		String query="INSERT INTO sii."+tableName+" (word,city,tfIdf) VALUES ";
		for(String city:citiesToScore.keySet())
			query+="('"+word+"','"+city+"','"+citiesToScore.get(city)+"'), ";

		query=query.substring(0,query.length()-2);
		query+=";";
		preparedStatement = connect.prepareStatement(query);
		preparedStatement.executeUpdate();

	}

	public void addUniqueWordsFromDB(HashSet<String> uniqWords) throws SQLException {
		preparedStatement = connect.prepareStatement("select distinct word from "+tableName+";");
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