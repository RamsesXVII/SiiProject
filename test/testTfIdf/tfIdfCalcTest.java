package testTfIdf;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import tfIdfUtility.CityToCount;
import tfIdfUtility.CityToCountUtility;
import tfIdfUtility.WordToCount;


public class tfIdfCalcTest {

	private HashMap<String, HashSet<CityToCount>>  words2citiesToCount;
	private HashMap<String,HashSet<WordToCount>> cities2wordsToCount; 

	
	private CityToCountUtility cUtility;
	private TermFrequencyCalcToTest tfCalc;


	@Before
	public void setUp() throws Exception {
		cUtility=new CityToCountUtility();
		cUtility.addItemsFromFile("test/testIdf.txt");
		
		this.words2citiesToCount=cUtility.getWordToCityCount();
		this.cities2wordsToCount=cUtility.convertToCitiesToWords(words2citiesToCount);
		this.tfCalc= new TermFrequencyCalcToTest(words2citiesToCount, cities2wordsToCount);
		//MILANO= 45.4654219$9.1859243
		//ROMA=41.9027835$12.4963655

	}

	@Test
	public void tfIdfCalc() {
		HashMap<String,HashMap<String,Double>>word2cityTfIdf=this.tfCalc.getTfIdfMap();
		
		Double tdidfApprox=Math.floor(word2cityTfIdf.get("ao").get("41.9027835$12.4963655") * 10000.0) / 10000.0;
		assertEquals(tdidfApprox,new Double(0.6931));
		
		tdidfApprox=Math.floor(word2cityTfIdf.get("bella").get("45.4654219$9.1859243") * 10000.0) / 10000.0;
		assertEquals(tdidfApprox,new Double(0));
		
		tdidfApprox=Math.floor(word2cityTfIdf.get("che").get("41.9027835$12.4963655") * 10000.0) / 10000.0;
		assertEquals(tdidfApprox,new Double(0.6931));
	}

	
}