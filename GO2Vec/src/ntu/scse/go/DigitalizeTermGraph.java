package ntu.scse.go;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ntu.scse.struct.CONST;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class DigitalizeTermGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String ontologyType = CONST.MF;
//		String inputTermIdList = "resources/go/term-id-" + ontologyType + ".txt";
////		String inputTermGraph = "resources/go/term-graph/" + ontologyType + "-extra-edge.txt";
////		String outputFile = "resources/go/term-graph/" + ontologyType + "-extra-edge-digit.txt";
//		
//		String inputTermGraph = "resources/go/term-graph/" + ontologyType + "-extra-edge.txt";
//		String outputFile = "resources/go/term-graph/" + ontologyType + "-extra-edge-digit.txt";
		
		String inputTermIdList = "resources/go/term-id.txt";
		String inputTermGraph = "resources/go/term-graph.txt";
		String outputFile = "resources/go/term-graph-digit.txt";
		
		DigitalizeTermGraph convert = new DigitalizeTermGraph();
		convert.digitalizeTermGraph(inputTermIdList, inputTermGraph, outputFile);

	}
	
	public void digitalizeTermGraph(String inputTermIdList, String inputTermGraph, String outputFile) {
		
		Map<String, Integer> termIdMap = loadTermId(inputTermIdList);
		
		BufferedReader br = IOProcess.newReader(inputTermGraph);
		String line;
		
		StringBuffer sb = new StringBuffer();
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				
				if(! termIdMap.containsKey(items[0]) || ! termIdMap.containsKey(items[1])) {
					System.out.println(line);
					continue;
				}
				
				String relation = items[2];
				if(relation.contains("regulates"))
					sb.append(termIdMap.get(items[1]) + "\t" + termIdMap.get(items[0]) + Setting.NEWLINE);
				else
					sb.append(termIdMap.get(items[0]) + "\t" + termIdMap.get(items[1]) + Setting.NEWLINE);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		IOProcess.saveFile(outputFile, sb.toString());
	}
	
	
	public Map<String, Integer> loadTermId(String inputTermIdFile) {
		BufferedReader br = IOProcess.newReader(inputTermIdFile);
		String line;
		
		/**
		 * Map<GOTermId, No>
		 * */
		Map<String, Integer> termIdMap = new HashMap<String, Integer>();
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				
				termIdMap.put(items[0], Integer.valueOf(items[1]));
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return termIdMap;
	}

}
