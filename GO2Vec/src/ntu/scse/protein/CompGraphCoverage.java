package ntu.scse.protein;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;

public class CompGraphCoverage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputProteinGraphFile = "resources/protein/protein-graph.list";
		String inputDerivedGraphFile = "resources/protein/protein-derived-graph/ProteinDrivedGraph-top20.txt";
		
		CompGraphCoverage comp = new CompGraphCoverage();
		comp.compGraphCoverage(inputProteinGraphFile, inputDerivedGraphFile);

	}
	
	public void compGraphCoverage(String inputProteinGraphFile, String inputDerivedGraphFile) {
		Map<String, List<String>> originalGraph = loadGraph(inputProteinGraphFile);
		
		Map<String, List<String>> derivedGraph = loadGraph(inputDerivedGraphFile);
		
		int totalCount = 0;
		int coverCount = 0;
		for(String nodeOne : originalGraph.keySet()) {
			List<String> nodeTwoList = originalGraph.get(nodeOne);
			
			totalCount += nodeTwoList.size();
			
			for(String nodeTwo : nodeTwoList) {
				boolean isCovered = isInGraph(nodeOne, nodeTwo, derivedGraph);
				
				if(isCovered)
					coverCount ++;
			}
		}
		
		System.out.println("total edges: " + totalCount);
		System.out.println("covered edges: " + coverCount);
		System.out.println("Percentage: " + BasicProcess.validDigit(coverCount * 1.0 / totalCount));
	}
	
	public Map<String, List<String>> loadGraph(String inputGraphFile) {
		BufferedReader br = IOProcess.newReader(inputGraphFile);
		String line;
		
		Map<String, List<String>> graph = new HashMap<String, List<String>>();
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				
				if(items.length < 2)
					continue;
				
				if(! graph.containsKey(items[0]))
					graph.put(items[0], new ArrayList<String>());
				
				graph.get(items[0]).add(items[1]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return graph;
	}
	
	public boolean isInGraph(String nodeOne, String nodeTwo, Map<String, List<String>> graph) {
		if(graph.containsKey(nodeOne) && graph.get(nodeOne).contains(nodeTwo) || graph.containsKey(nodeTwo) && graph.get(nodeTwo).contains(nodeOne))
			return true;
		return false;
	}

}
