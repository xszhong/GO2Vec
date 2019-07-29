package ntu.scse.ppi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class ExtractPPI {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputPPIFile = "resources/ppi/9606.protein.links.v11.0.txt";
		String inputGOAFile = "resources/ppi/UniProt-9606-human-annotation.txt";
		String outputDir = "resources/ppi/9606/";
		String name = "9606";
		
		ExtractPPI extractor = new ExtractPPI();
		extractor.extractPPI(inputPPIFile, inputGOAFile, outputDir, name);

	}
	
	private void extractPPI(String inputFile, String inputGOAFile, String outputDir, String name) {
		
		Map<String, String> idMap = loadProteinId(inputGOAFile);
		
		BufferedReader br = IOProcess.newReader(inputFile);
		String line;
		
		Set<String> proteinSet = new HashSet<String>();
		
		StringBuffer sb = new StringBuffer();
		
		try {
			while((line = br.readLine()) != null) {
				if(line.startsWith("protein1"))
					continue;
				
				String[] items = line.split(" |\t");
				
				String strProteinOne = items[0];
				String strProteinTwo = items[1];
				
				if(! idMap.containsKey(strProteinOne) || ! idMap.containsKey(strProteinTwo))
					continue;
				
				String proteinOne = idMap.get(strProteinOne);
				String proteinTwo = idMap.get(strProteinTwo);
				
				proteinSet.add(proteinOne);
				proteinSet.add(proteinTwo);
				
				sb.append(proteinOne + "\t" + proteinTwo + "\t1" + Setting.NEWLINE);
				
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		IOProcess.saveFile(outputDir + name + "-ppi.txt", sb.toString());
		
		StringBuffer idSb = new StringBuffer();
		List<String> list = BasicProcess.sortSet(proteinSet);
		for(int i = 1; i <= list.size(); i++) {
			idSb.append(list.get(i - 1) + "\t" + i + Setting.NEWLINE);
		}
		
		IOProcess.saveFile(outputDir + name + "-protein-id.txt", idSb.toString());
	}
	
	private Map<String, String> loadProteinId(String inputGOAFile) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		BufferedReader br = IOProcess.newReader(inputGOAFile);
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				if(line.startsWith("Entry"))
					continue;
				
				String[] items = line.trim().split("\t");
				
				if(items.length < 3)
					continue;
				
				map.put(items[1], items[0]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return map;
	}
	

}
