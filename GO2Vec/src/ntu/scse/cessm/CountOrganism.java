package ntu.scse.cessm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;

public class CountOrganism {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputFile = "resources/cessm/cessm-uniprot-yourlist.txt";
		CountOrganism count = new CountOrganism();
		count.countProteinType(inputFile);		

	}
	
	public void countProteinType(String inputFile) {
		BufferedReader br = IOProcess.newReader(inputFile);
		String line;
		
		Map<String, Integer> typeMap = new HashMap<String, Integer>();
		
		try {
			while((line = br.readLine()) != null) {
				if(line.startsWith("Entry"))
					continue;
				
				String[] items = line.split("\t");
				
				String type = items[2].split("_")[1];
				
				int count = 1;
				if(typeMap.containsKey(type))
					count += typeMap.get(type);
				typeMap.put(type, count);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {}
		
		List<Map.Entry<String, Integer>> list = BasicProcess.sortValues(typeMap, false);
		for(Map.Entry<String, Integer> entry : list) {
			System.out.println(entry.getKey() + "\t&\t" + entry.getValue() + "\t&");
		}
	}

}
