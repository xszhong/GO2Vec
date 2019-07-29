package ntu.scse.preprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class ProteinGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputProteinFile = "resources/protein/protein-id.txt";
		String inputGraphFile = "resources/protein/protein-graph.list";
		String outputFile = "resources/protein/protein-graph.txt";
		
		ProteinGraph graph = new ProteinGraph();
		graph.induceProteinGraph(inputProteinFile, inputGraphFile, outputFile);

	}
	
	public void induceProteinGraph(String inputProteinFile, String inputGraphFile, String outputFile) {
		Map<String, String> proteinMap = loadProtein(inputProteinFile);
		
		BufferedReader br = IOProcess.newReader(inputGraphFile);
		String line;
		StringBuffer sb = new StringBuffer();
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.trim().split("\t");
				if(items.length < 2)
					continue;
				
				if(!proteinMap.containsKey(items[0]) || !proteinMap.containsKey(items[1]))
					continue;
				
				String node1 = proteinMap.get(items[0]);
				String node2 = proteinMap.get(items[1]);
				sb.append(node1 + "\t" + node2 + Setting.NEWLINE);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		IOProcess.saveFile(outputFile, sb.toString());
	}
	
	public Map<String, String> loadProtein(String inputFile){
		BufferedReader br = IOProcess.newReader(inputFile);
		String line;
		
		Map<String, String> proteinMap = new HashMap<String, String>();
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				if(items.length < 2)
					continue;
				
				proteinMap.put(items[0], items[1]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return proteinMap;
	}

}
