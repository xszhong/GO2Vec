package ntu.scse.goa;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ntu.scse.struct.CONST;
import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class CompTermFrequency {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inputDir = "resources/goa/annotation/";
		String outputDir = "resources/goa/frequency/";
		String goType = CONST.MF;
		CompTermFrequency comp = new CompTermFrequency();
		comp.compTermFrequency(inputDir, outputDir, goType);
	}
	
	public void compTermFrequency(String inputDir, String outputDir, String goType) {
		inputDir = IOProcess.checkPath(inputDir);
		outputDir = IOProcess.checkPath(outputDir);
		
		Set<String> termSet = loadTerm("resources/go/term-id-" + goType + ".txt");
		
		Map<String, Integer> termCountMap = new HashMap<String, Integer>();
		Map<String, Double> termFreqMap = new HashMap<String, Double>();
		int totalTermCount = 0;
		
		Map<String, Map<String, Integer>> jointTermCountMap = new HashMap<String, Map<String, Integer>>();
		int totalJointTermCount = 0;
		
		File[] files = IOProcess.getFiles(inputDir);
		
		for(File file : files) {
			
			BufferedReader br = IOProcess.newReader(file);
			String line;
			try {
				while((line = br.readLine()) != null) {
					String[] items = line.split(" |\t");
					String[] terms = items[1].split(",|\\|");
					
					Map<String, Integer> termMap = new HashMap<String, Integer>();
					
					for(String term : terms) {
						if(! term.startsWith("GO") || ! termSet.contains(term))
							continue;
						
						totalTermCount ++;
						
						int count = 1;
						if(termMap.containsKey(term))
							count += termMap.get(term);
						termMap.put(term, count);
					}
					
					List<Map.Entry<String, Integer>> termList = BasicProcess.sortKeys(termMap);
					
					for(int i = 0; i < termList.size(); i++) {
						
						Map.Entry<String, Integer> entryOne = termList.get(i);
						
						/**
						 * Calculate the term frequency
						 * */
						String termOne = entryOne.getKey();
						Integer countOne = entryOne.getValue();
						
						int termCount = countOne;
						if(termCountMap.containsKey(termOne))
							termCount += termCountMap.get(termOne);
						termCountMap.put(termOne, termCount);
						
						
						/**
						 * Calculate the joint frequency
						 * */
						if(! jointTermCountMap.containsKey(termOne))
							jointTermCountMap.put(termOne, new HashMap<String, Integer>());
						
						for(int j = i + 1; j < termList.size(); j++) {
							Map.Entry<String, Integer> entryTwo = termList.get(j);
							String termTwo = entryTwo.getKey();
							Integer countTwo = entryTwo.getValue();
							
							int jointCount = countOne;
							if(jointCount < countTwo)
								jointCount = countTwo;
							
							totalJointTermCount += jointCount;
							
							if(jointTermCountMap.get(termOne).containsKey(termTwo))
								jointCount += jointTermCountMap.get(termOne).get(termTwo);
							jointTermCountMap.get(termOne).put(termTwo, jointCount);
							
						}
					}
					
					termMap.clear();
					termList.clear();
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally { }
		}
		
		/**
		 * Term frequency
		 * */
		List<Map.Entry<String, Integer>> termList = BasicProcess.sortKeys(termCountMap);
		StringBuffer termSb = new StringBuffer();
		for(Map.Entry<String, Integer> entry : termList) {
			String term = entry.getKey();
			Integer count = entry.getValue();
			double freq = BasicProcess.validDigit(count * 1.0 / totalTermCount);
			termFreqMap.put(term, Double.valueOf(freq));
			termSb.append(term + "\t" + count + "\t" + freq + Setting.NEWLINE);
		}
		IOProcess.saveFile(outputDir + "term-frequency-" + goType + ".txt", termSb.toString());
		
		/**
		 * Joint term frequency
		 * */
		List<Map.Entry<String, Map<String, Integer>>> jointTermList = BasicProcess.sortKeys(jointTermCountMap);
		StringBuffer jointSb = new StringBuffer();
		StringBuffer pmiSb = new StringBuffer();
		for(Map.Entry<String, Map<String, Integer>> entry : jointTermList) {
			String termOne = entry.getKey();
			
			double freqOne = termFreqMap.get(termOne);
			
			Map<String, Integer> subTermMap = entry.getValue();
			List<Map.Entry<String, Integer>> subTermList = BasicProcess.sortKeys(subTermMap);
			for(Map.Entry<String, Integer> subEntry : subTermList) {
				String termTwo = subEntry.getKey();
				Integer jointCount = subEntry.getValue();
				
				double jointFreq = BasicProcess.validDigit(jointCount * 1.0 / totalJointTermCount);
				jointSb.append(termOne + "\t" + termTwo + "\t" + jointCount + "\t" + jointFreq + Setting.NEWLINE);
				
				double freqTwo = termFreqMap.get(termTwo);
				double pmi = BasicProcess.validDigit(Math.log(jointFreq / (freqOne * freqTwo)));
				pmiSb.append(termOne + "\t" + termTwo + "\t" + pmi + Setting.NEWLINE);
			}
		}
		IOProcess.saveFile(outputDir + "joint-term-frequency-" + goType + ".txt", jointSb.toString());
		IOProcess.saveFile(outputDir + "term-pmi-" + goType + ".txt", pmiSb.toString());
	}
	
	public Set<String> loadTerm(String inputFile) {
		BufferedReader br = IOProcess.newReader(inputFile);
		String line;
		Set<String> termSet = new HashSet<String>();
		
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				termSet.add(items[0]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return termSet;
	}

}
