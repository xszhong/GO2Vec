package ntu.scse.protein;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class DeriveProteinGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputCosDir = "resources/protein/protein-cos-via-avgemb";
		int topN = 20;
		String outputProteinGraphDir = "resources/protein/protein-derived-graph";
		
		DeriveProteinGraph derivor = new DeriveProteinGraph();
		derivor.deriveProteinGraph(inputCosDir, topN, outputProteinGraphDir);

	}
	
	public void deriveProteinGraph(String inputCosDir, int topN, String outputPreteinGraphDir) {
		inputCosDir = IOProcess.checkPath(inputCosDir);
		outputPreteinGraphDir = IOProcess.checkPath(outputPreteinGraphDir);
		
		File[] files = IOProcess.getFiles(inputCosDir);
		
		StringBuffer sb = new StringBuffer();
		
		for(File file : files) {
			String filename = file.getName();
			
			BufferedReader br = IOProcess.newReader(file);
			String line;
			
			Map<String, Double> cosMap = new HashMap<String, Double>();
			
			try {
				while((line = br.readLine()) != null) {
					String[] items = line.split("\t");
					
					cosMap.put(items[0], Double.valueOf(items[1]));
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally { }
			
			List<Map.Entry<String, Double>> cosList = BasicProcess.sortValues(cosMap, false);
			
			for(int i = 0; i < topN && i < cosList.size(); i++) {
				Map.Entry<String, Double> entry = cosList.get(i);
				
				sb.append(filename + "\t" + entry.getKey() + "\t" + entry.getValue() + Setting.NEWLINE);
			}
			
			//sb.append(Setting.NEWLINE);
		}
		
		IOProcess.saveFile(outputPreteinGraphDir + "ProteinDrivedGraph-top" + topN + ".txt", sb.toString());
	}

}
