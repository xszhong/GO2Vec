package ntu.scse.cessm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class InduceProteinId {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String inputProteinPairs = "resources/cessm/proteinpairs.txt";
		String outputProteinId = "resources/cessm/cessm-protein-id.txt";
		
		InduceProteinId induce = new InduceProteinId();
		induce.induceProteinId(inputProteinPairs, outputProteinId);
		
	}
	
	public void induceProteinId(String inputProteinPairs, String outputProteinId) {
		BufferedReader br = IOProcess.newReader(inputProteinPairs);
		String line;
		
		Set<String> proteinSet = new HashSet<String>();
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				for(String item : items)
					proteinSet.add(item);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		List<String> list = BasicProcess.sortSet(proteinSet);
		StringBuffer sb = new StringBuffer();
		int index = 1;
		for(String protein : list) {
			sb.append(protein + "\t" + index + Setting.NEWLINE);
			index ++;
		}
		
		IOProcess.saveFile(outputProteinId, sb.toString());
	}

}
