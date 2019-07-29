package ntu.scse.cessm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import ntu.scse.util.IOProcess;

public class FindProtein {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputProteinId = "resources/cessm/allproteinsBPanno.txt";
		String inputProteinpairs = "resources/cessm/proteinpairs.txt";
		
		FindProtein find = new FindProtein();
		find.findProtein(inputProteinId, inputProteinpairs);

	}
	
	public void findProtein(String inputProteinId, String inputProteinpairs) {
		Set<String> proteinSet = loadProteinId(inputProteinId);
		
		Set<String> uncoverSet = new HashSet<String>();
		
		BufferedReader br = IOProcess.newReader(inputProteinpairs);
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				
				for(String item : items) {
					if(! proteinSet.contains(item)) {
						uncoverSet.add(item);
					}
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Uncover size: " + uncoverSet.size());
		for(String item : uncoverSet)
			System.out.println(item);
	}
	
	public Set<String> loadProteinId(String inputProteinId){
		BufferedReader br = IOProcess.newReader(inputProteinId);
		String line;
		
		Set<String> proteinSet = new HashSet<String>();
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				proteinSet.add(items[0]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return proteinSet;
	}

}
