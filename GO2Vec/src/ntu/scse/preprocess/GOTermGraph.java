package ntu.scse.preprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class GOTermGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputGOTermList = "resources/geneontology/geneterm/term-id.txt";
		String inputGOTermGraph = "resources/geneontology/geneterm/term-graph.list";
		String outputFile = "resources/geneontology/geneterm/term-graph.txt";
		
		GOTermGraph go = new GOTermGraph();
		go.induceGOTermGraph(inputGOTermList, inputGOTermGraph, outputFile);

	}
	
	public void induceGOTermGraph(String inputGOTermList, String inputGOTermGraph, String outputFile) {
		
		Map<String, String> termIdMap = loadGOTermId(inputGOTermList);
		
		BufferedReader br = IOProcess.newReader(inputGOTermGraph);
		String line;
		
		StringBuffer sb = new StringBuffer();
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				
				sb.append(termIdMap.get(items[0]) + "\t" + termIdMap.get(items[1]) + Setting.NEWLINE);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		IOProcess.saveFile(outputFile, sb.toString());
	}
	
	public Map<String, String> loadGOTermId(String inputGOTermFile) {
		BufferedReader br = IOProcess.newReader(inputGOTermFile);
		String line;
		
		Map<String, String> idMap = new HashMap<String, String>();
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				
				idMap.put(items[0], items[1]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return idMap;
	}

}
