package ntu.scse.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ntu.scse.struct.CONST;
import ntu.scse.util.IOProcess;

public class TermId {
	private static Map<String, String> bpTermIdMap;
	private static Map<String, String> bpNumIdMap;
	
	private static Map<String, String> ccTermIdMap;
	private static Map<String, String> ccNumIdMap;
	
	private static Map<String, String> mfTermIdMap;
	private static Map<String, String> mfNumIdMap;
	
	private static Map<String, String> goTermIdMap;
	private static Map<String, String> goNumIdMap;
	
	static {
		bpTermIdMap = new HashMap<String, String>();
		bpNumIdMap = new HashMap<String, String>();
		
		ccTermIdMap = new HashMap<String, String>();
		ccNumIdMap = new HashMap<String, String>();
		
		mfTermIdMap = new HashMap<String, String>();
		mfNumIdMap = new HashMap<String, String>();
		
		goTermIdMap = new HashMap<String, String>();
		goNumIdMap = new HashMap<String, String>();
		
		loadIdMap(CONST.BP_TermIdFile, CONST.BP);
		loadIdMap(CONST.CC_TermIdFile, CONST.CC);
		loadIdMap(CONST.MF_TermIdFile, CONST.MF);
		
		loadIdMap(CONST.GO_TermIdFile, CONST.GO);
	}

	private static void loadIdMap(String inputTermIdFile, String type) {
		BufferedReader br = IOProcess.newReader(inputTermIdFile);
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				/**
				 * GO:0000001	biological_process	1
				 * */
				String[] items = line.split("\t");
				
				if(type.equals(CONST.BP)) {
					bpTermIdMap.put(items[0], items[2]);
					bpNumIdMap.put(items[2], items[0]);
				}else if(type.equals(CONST.CC)) {
					ccTermIdMap.put(items[0], items[2]);
					ccNumIdMap.put(items[2], items[0]);
				}else if(type.equals(CONST.MF)) {
					mfTermIdMap.put(items[0], items[2]);
					mfNumIdMap.put(items[2], items[0]);
				}else if(type.equals(CONST.GO)) {
					goTermIdMap.put(items[0], items[1]);
					goNumIdMap.put(items[1], items[0]);
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
	}
	
	public static String getTermId(String numId, String type) {
		if(type.equals(CONST.BP)) {
			if(bpNumIdMap.containsKey(numId))
				return bpNumIdMap.get(numId);
		}else if(type.equals(CONST.CC)) {
			if(ccNumIdMap.containsKey(numId))
				return ccNumIdMap.get(numId);
		}else if(type.equals(CONST.MF)) {
			if(mfNumIdMap.containsKey(numId))
				return mfNumIdMap.get(numId);
		}else if(type.equals(CONST.GO)) {
			if(goNumIdMap.containsKey(numId))
				return goNumIdMap.get(numId);
		}
		
		return null;
	}
	
	public static String getNumId(String termId, String type) {
		if(type.equals(CONST.BP)) {
			if(bpTermIdMap.containsKey(termId))
				return bpTermIdMap.get(termId);
		}else if(type.equals(CONST.CC)) {
			if(ccTermIdMap.containsKey(termId))
				return ccTermIdMap.get(termId);
		}else if(type.equals(CONST.MF)) {
			if(mfTermIdMap.containsKey(termId))
				return mfTermIdMap.get(termId);
		}else if(type.equals(CONST.GO)) {
			if(goTermIdMap.containsKey(termId))
				return goTermIdMap.get(termId);
		}
		return null;
	}
}
