package ntu.scse.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ntu.scse.struct.CONST;
import ntu.scse.struct.GOTerm;
import ntu.scse.util.IOProcess;

public class TermEmbedding {
	/**
	 * Map<Term ID, GOTerm>
	 * */
	private static Map<String, GOTerm> bpTermMap;
	private static Map<String, GOTerm> ccTermMap;
	private static Map<String, GOTerm> mfTermMap;
	
	private static Map<String, GOTerm> goTermMap;
	
	static {
		bpTermMap = loadTermEmbedding(CONST.BP_Term128DEmbeddingFile, CONST.BP);
		ccTermMap = loadTermEmbedding(CONST.CC_Term128DEmbeddingFile, CONST.CC);
		mfTermMap = loadTermEmbedding(CONST.MF_Term128DEmbeddingFile, CONST.MF);
		
		goTermMap = loadTermEmbedding(CONST.GO_Term128DEmbeddingFile, CONST.GO);
	}
	
	private static Map<String, GOTerm> loadTermEmbedding(String inputTermEmbeddingFile, String type) {
		BufferedReader br = IOProcess.newReader(inputTermEmbeddingFile);
		String line;
		
		Map<String, GOTerm> termMap = new HashMap<String, GOTerm>();
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split(" |\t");
				if(items.length == 2 || items.length < 1)
					continue;
				
				String numId = items[0];
				String termId = TermId.getTermId(numId, type);
				
				List<Double> embedList = new ArrayList<Double>();
				
				for(int i = 1; i < items.length; i++)
					embedList.add(Double.valueOf(items[i]));
				
				GOTerm goTerm = new GOTerm(termId, numId, type, embedList);
				
				termMap.put(termId, goTerm);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return termMap;
	}
	
	public static Map<String, GOTerm> getBPTermMap(){
		return bpTermMap;
	}
	
	public static Map<String, GOTerm> getCCTermMap(){
		return ccTermMap;
	}
	
	public static Map<String, GOTerm> getMFTermMap(){
		return mfTermMap;
	}
	
	public static Map<String, GOTerm> getGOTermMap(){
		return goTermMap;
	}
	
	public static GOTerm getTerm(String termId, String type) {
		if(type.equals(CONST.BP) && bpTermMap.containsKey(termId))
			return bpTermMap.get(termId);
		else if(type.equals(CONST.CC) && ccTermMap.containsKey(termId))
			return ccTermMap.get(termId);
		else if(type.equals(CONST.MF) && mfTermMap.containsKey(termId))
			return mfTermMap.get(termId);
		else if(type.equals(CONST.GO) && goTermMap.containsKey(termId))
			return goTermMap.get(termId);
		return null;
	}
}
