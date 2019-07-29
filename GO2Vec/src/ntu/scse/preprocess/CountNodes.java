package ntu.scse.preprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class CountNodes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CountNodes count = new CountNodes();
		String inputFile = "resources/physical_edgelist";
		String outputFile = "resources/protein-node.list";
		
		count.countNotes(inputFile, outputFile);

	}
	
	public void countNotes(String inputFile, String outputFile) {
		BufferedReader br = IOProcess.newReader(inputFile);
		String line;
		
		Set<String> nodeSet = new HashSet<String>();
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				
				for(String node : items)
					nodeSet.add(node);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		List<String> list = BasicProcess.sortSet(nodeSet);
		StringBuffer sb = new StringBuffer();
		int index = 1;
		for(String node : list) {
			if(node.length() < 1)
				continue;
			sb.append(node + "\t" + index + Setting.NEWLINE);
			index ++;
		}
		
		IOProcess.saveFile(outputFile, sb.toString());
		
		System.out.println(nodeSet.size());
		
	}

}
