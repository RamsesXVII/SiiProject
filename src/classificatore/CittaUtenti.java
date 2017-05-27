package classificatore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CittaUtenti {
	
	/*prende in input un file con una linea del tipo: id Utente e città
	 * restituisce un file con città e lista di IdUtente associati
	 */
	public static void main(String [] args)throws IOException {
		
		FileReader input = new FileReader("resources/training_set_users.txt");
		BufferedReader bufRead = new BufferedReader(input);
		
		File out1 = new File("resources/city2user.txt");
		FileWriter fw1 = new FileWriter(out1,true);
		BufferedWriter city2user = new BufferedWriter(fw1);
		
		String linea=null;
		String[] campi=null;
		
		List<String> tmp;
		Map<String, List<String>> cittaUtenti;

		cittaUtenti = new HashMap<String, List<String>>();

		while ((linea = bufRead.readLine())!=null){
			
			campi=linea.split("	|\\, ");  //da cambiare con il nostro file
			tmp = cittaUtenti.get(campi[1]);
			
			if (tmp==null)
				tmp = new LinkedList<String>();
			
			tmp.add(campi[0]);
			cittaUtenti.put(campi[1], tmp);
		}
		
		Set<String> listaCitta = cittaUtenti.keySet();
		
		for(String citta : listaCitta){
			
			city2user.write(citta + "\t");
			List<String> users = cittaUtenti.get(citta);
			city2user.write(users.get(0));
			users.remove(0);
			
			for(String utente : users){
				city2user.write(", " + utente);
			}
			
			city2user.write("\n");
			city2user.flush();
		}
		
		city2user.close();
		bufRead.close();
	}
}