package ntu.scse.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ntu.scse.util.IOProcess;

public class ProteinId {
	private static String inputProteinIdFile;
	
	private static Map<String, String> proteinIdMap;
	private static Map<String, String> numIdMap;
	
	static {
		inputProteinIdFile = "resources/protein/protein-id.txt";
		proteinIdMap = new HashMap<String, String>();
		numIdMap = new HashMap<String, String>();
		loadIdMap(inputProteinIdFile);
	}

	private static void loadIdMap(String inputProteinIdFile) {
		BufferedReader br = IOProcess.newReader(inputProteinIdFile);
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				proteinIdMap.put(items[0], items[1]);
				numIdMap.put(items[1], items[0]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
	}
	
	public static String getProteinId(String numId) {
		if(numIdMap.containsKey(numId))
			return numIdMap.get(numId);
		return null;
	}
	
	public static String getNumId(String proteinId) {
		if(proteinIdMap.containsKey(proteinId))
			return proteinIdMap.get(proteinId);
		return null;
	}
}
