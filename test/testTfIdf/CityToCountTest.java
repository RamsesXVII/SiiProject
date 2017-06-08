package testTfIdf;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import tfIdfUtility.CityToCount;
import tfIdfUtility.CityToCountUtility;
import tfIdfUtility.WordToCount;


public class CityToCountTest {

	private CityToCount romeOne;
	private CityToCount milanOne;
	private CityToCount parisOne;

	private CityToCountUtility cUtility;


	private String parolaDaInserireAo;
	private String parolaDaInserireBella;
	private String nomeCittaRome;
	private String nomeCittaMilan;
	private String nomeCittaFrosinone;


	@Before
	public void setUp() throws Exception {
		this.romeOne= new CityToCount("rome", 1);
		this.milanOne= new CityToCount("milan", 1);
		this.parisOne= new CityToCount("paris", 1);


		this.cUtility= new CityToCountUtility();

		this.parolaDaInserireBella="bella";
		this.parolaDaInserireAo="ao";

		this.nomeCittaFrosinone="frosinone";
		this.nomeCittaRome="rome";
		this.nomeCittaMilan="milan";



	}

	@Test
	public void incrementTest() {

		assertEquals(this.romeOne.getCount(),new Integer(1));
		assertEquals(this.milanOne.getCount(),new Integer(1));
		assertEquals(this.parisOne.getCount(),new Integer(1));

		this.romeOne.increment();
		this.milanOne.increment();
		this.parisOne.increment();

		assertEquals(this.romeOne.getCount(),new Integer(2));
		assertEquals(this.milanOne.getCount(),new Integer(2));
		assertEquals(this.parisOne.getCount(),new Integer(2));
	}

	@Test
	public void mapAddingTest() {


		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaRome);

		assertTrue(cUtility.getWordToCityCount().containsKey(parolaDaInserireAo));
		assertTrue(cUtility.getWordToCityCount().get(parolaDaInserireAo).contains(new CityToCount(nomeCittaRome, 1)));

		assertFalse(cUtility.getWordToCityCount().get(parolaDaInserireAo).contains(new CityToCount(nomeCittaMilan, 1)));

		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaMilan);

		assertTrue(cUtility.getWordToCityCount().containsKey(parolaDaInserireAo));
		assertTrue(cUtility.getWordToCityCount().get(parolaDaInserireAo).contains(new CityToCount(nomeCittaMilan, 1)));

		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaRome);
		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaRome);
		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaRome);

		cUtility.addWordToCityCount(parolaDaInserireBella, nomeCittaFrosinone);

		assertTrue(cUtility.getWordToCityCount().containsKey(parolaDaInserireBella));
		assertTrue(cUtility.getWordToCityCount().get(parolaDaInserireBella).contains(new CityToCount(nomeCittaFrosinone, 1)));

		HashSet<CityToCount> citiesToCount=cUtility.getWordToCityCount().get(parolaDaInserireAo);

		assertEquals(citiesToCount.size(), 2);

		int numberMilan=0;
		int numberRome=0;
		for(CityToCount cityToCount:citiesToCount){
			if(cityToCount.getCity().equals("milan")){		
				numberMilan+=1;
				assertEquals(cityToCount.getCount(), new Integer(1));}

			if(cityToCount.getCity().equals("rome")){
				numberRome+=1;
				assertEquals(cityToCount.getCount(), new Integer(4));}
		}

		assertEquals(numberMilan, 1);
		assertEquals(numberRome, 1);

	}

	@Test
	public void mapAddingTestFromFile() throws FileNotFoundException, IOException {
		this.cUtility.addItemsFromFile("test/testTweets.txt");
		//NOTA BENE: ao, bella, che sono parole frequenti nei tweet americani
		assertEquals(cUtility.getWordToCityCount().keySet().size(),3);
	}
	
	@Test
	public void cities2words2countTest() throws FileNotFoundException, IOException {
		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaRome);
		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaRome);
		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaRome);
		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaRome);
		
		cUtility.addWordToCityCount(parolaDaInserireBella, nomeCittaFrosinone);
		cUtility.addWordToCityCount(parolaDaInserireAo, nomeCittaFrosinone);

		
		HashMap<String, HashSet<WordToCount>> cities2words2count=cUtility.convertToCitiesToWords(cUtility.getWordToCityCount());

		assertEquals(new Integer(cities2words2count.keySet().size()), new Integer(2));
		assertEquals(new Integer(cities2words2count.get("rome").size()), new Integer(1));
		assertEquals(new Integer(cities2words2count.get("frosinone").size()), new Integer(2));
		assertEquals(cities2words2count.get("frosinone").iterator().next().getWord(), "bella");
		
		
	}

}