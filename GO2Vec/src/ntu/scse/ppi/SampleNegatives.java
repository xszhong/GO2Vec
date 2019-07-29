package ntu.scse.ppi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class SampleNegatives {
	
	private int posSize = 0;
	private Random rand = new Random();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputProteinId = "resources/ppi/9606/9606-protein-id.txt";
		String inputPositiveFile = "resources/ppi/9606/9606-ppi.txt";
		String outputNegativeFile = "resources/ppi/9606/9606-negative.txt";
		
		SampleNegatives sample = new SampleNegatives();
		sample.sampleNegative(inputProteinId, inputPositiveFile, outputNegativeFile);

	}
	
	private void sampleNegative(String inputProteinId, String inputPositiveFile, String outputNegFile ) {
		List<String> proteinIdList = loadProteinId(inputProteinId);
		
		int idSize = proteinIdList.size();
		
		Map<String, Set<String>> posMap = loadPOSMap(inputPositiveFile);
		
		Map<String, Set<String>> negMap = new HashMap<String, Set<String>>();
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < posSize; i ++) {
			int p1 = rand.nextInt(idSize);
			String proteinOne = proteinIdList.get(p1);
			
			boolean flag = false;
			
			int p2 = rand.nextInt(idSize);
			String proteinTwo = proteinIdList.get(p2);
			
			while (! flag) {
				p2 = rand.nextInt(idSize);
				
				if(p2 == p1)
					continue;
				
				proteinTwo = proteinIdList.get(p2);
				
				if(posMap.containsKey(proteinOne) && posMap.get(proteinOne).contains(proteinTwo) || posMap.containsKey(proteinTwo) && posMap.get(proteinTwo).contains(proteinOne))
					continue;
				
				if(negMap.containsKey(proteinOne) && negMap.get(proteinOne).contains(proteinTwo) || negMap.containsKey(proteinTwo) && negMap.get(proteinTwo).contains(proteinOne))
					continue;
				
				flag = true;
			}
			
			if(! negMap.containsKey(proteinOne))
				negMap.put(proteinOne, new HashSet<String>());
			negMap.get(proteinOne).add(proteinTwo);
			
			if(! negMap.containsKey(proteinTwo))
				negMap.put(proteinTwo, new HashSet<String>());
			negMap.get(proteinTwo).add(proteinOne);
			
			sb.append(proteinOne + "\t" + proteinTwo + "\t0" + Setting.NEWLINE);
		}
		
		IOProcess.saveFile(outputNegFile, sb.toString());
	}
	
	private Map<String, Set<String>> loadPOSMap(String inputPositiveFile){
		Map<String, Set<String>> posMap = new HashMap<String, Set<String>>();
		
		BufferedReader br = IOProcess.newReader(inputPositiveFile);
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				posSize ++;
				
				String[] items = line.split("\t");
				
				if(! posMap.containsKey(items[0]))
					posMap.put(items[0], new HashSet<String>());
				posMap.get(items[0]).add(items[1]);
				
				if(! posMap.containsKey(items[1]))
					posMap.put(items[1], new HashSet<String>());
				posMap.get(items[1]).add(items[0]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return posMap;
	}
	
	private List<String> loadProteinId(String inputProteinId){
		List<String> proteinIdList = new ArrayList<String>();
		BufferedReader br = IOProcess.newReader(inputProteinId);
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				String protein = line.split("\t")[0];
				proteinIdList.add(protein);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return proteinIdList;
	}

}
