package ntu.scse.ppi;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ntu.scse.struct.CONST;
import ntu.scse.struct.GOTerm;
import ntu.scse.tool.Cosine;
import ntu.scse.tool.TermEmbedding;
import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class CompMhdProteinCos {
	
	/**
	 * Map<TermOne, Map<TermTwo, cos>>
	 * */
	Map<String, Map<String, Double>> termCosMap = new HashMap<String, Map<String, Double>>();

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		String goType = CONST.GO;
		
		String inputProteinpairsFile = "resources/ppi/4932/4932-pos-neg-ppi.txt";
		String inputProteinAnnotationFile = "resources/ppi/goa-4932-yeast.txt";
		
		String outputFile = "resources/ppi/mhd/pos-neg-ppi-4932-yeast-" + goType + "-mhd.txt";
		
		CompMhdProteinCos comp = new CompMhdProteinCos();
		comp.compMhdProteinCos(inputProteinpairsFile, inputProteinAnnotationFile, goType, outputFile);
		
//		String outputTermPairFile = "resources/cessm/termpair/cessm-term-pair-" + goType + ".txt";
//		comp.outputTermCos(outputTermPairFile);

	}
	
	public void compMhdProteinCos(String inputProteinpairsFile, String inputProteinAnnotationFile, String goType, String outputFile) {
		
		Map<String, List<GOTerm>> proteinAnnotationMap = loadProteinAnnotation(inputProteinAnnotationFile, goType);
		
		BufferedReader br = IOProcess.newReader(inputProteinpairsFile);
		String line;
		StringBuffer sb = new StringBuffer();
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				if (items.length < 2) {
					System.out.println("The proteinpair is incorrect: " + line);
					continue;
				}
				
				String proteinOne = items[0];
				String proteinTwo = items[1];
				
				double mhd = 0.0;
				if(! proteinAnnotationMap.containsKey(proteinOne) || ! proteinAnnotationMap.containsKey(proteinTwo)) {
					sb.append(line + "\t" + mhd + Setting.NEWLINE);
					continue;
				}
				
				List<GOTerm> termListOne = proteinAnnotationMap.get(proteinOne);
				List<GOTerm> termListTwo = proteinAnnotationMap.get(proteinTwo);
				
				mhd = BasicProcess.validDecimal(compMHD(termListOne, termListTwo, goType));
				
//				if(mhd == 0.0)
//					sb.append(line + "\t" + mhd + Setting.NEWLINE);
//				else
//					sb.append(line + "\t" + mhd + Setting.NEWLINE);
				
				if(mhd == 0.0)
					continue;
				
				sb.append(mhd + "\t" + items[2] + Setting.NEWLINE);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		IOProcess.saveFile(outputFile, sb.toString());
	}
	
	public double compMHD(List<GOTerm> termListOne, List<GOTerm> termListTwo, String type) {
		int sizeOne = termListOne.size();
		int sizeTwo = termListTwo.size();
		
		if(sizeOne == 0 || sizeTwo == 0)
			return 0.0;
		
		double sumMhdOne = 0;
		for(GOTerm termOne : termListOne) {
			double maxTermCos = -1.0;
			for(GOTerm termTwo : termListTwo) {
				
				double termCos = compTermCos(termOne, termTwo);
				if(termCos > maxTermCos)
					maxTermCos = termCos;
			}
			sumMhdOne += maxTermCos;
		}
		double avgMhdOne = sumMhdOne / sizeOne;
		
		double sumMhdTwo = 0;
		for(GOTerm termTwo : termListTwo) {
			double maxTermCos = -1.0;
			for(GOTerm termOne : termListOne) {
				double termCos = compTermCos(termTwo, termOne);
				if(termCos > maxTermCos)
					maxTermCos = termCos;
			}
			sumMhdTwo += maxTermCos;
		}
		double avgMhdTwo = sumMhdTwo / sizeTwo;
		
		if(avgMhdOne < avgMhdTwo)
			return avgMhdOne;
		else
			return avgMhdTwo;
		
	}
	
	public double compTermCos(GOTerm termOne, GOTerm termTwo) {
		String termOneId = termOne.getTermId();
		String termTwoId = termTwo.getTermId();
		
		int com = termOneId.compareTo(termTwoId);
		
		if(com > 0) {
			String temp = termOneId;
			termOneId = termTwoId;
			termTwoId = temp;
		}
		
		if(! termCosMap.containsKey(termOneId))
			termCosMap.put(termOneId, new HashMap<String, Double>());
		if(termCosMap.get(termOneId).containsKey(termTwoId))
			return termCosMap.get(termOneId).get(termTwoId);
		
		double cos = Cosine.cosine(termOne, termTwo);
		termCosMap.get(termOneId).put(termTwoId, cos);
		
		return cos;
	}
	
	public Map<String, List<GOTerm>> loadProteinAnnotation(String inputFile, String type) {

		Map<String, List<GOTerm>> proteinMap = new HashMap<String, List<GOTerm>>();
		
		BufferedReader br = IOProcess.newReader(inputFile);
		String line;
		
		try {
			while((line = br.readLine()) != null) {
				if(line.startsWith("Entry"))
					continue;
				
				String[] items = line.split("\t");
				
				List<GOTerm> termList = new ArrayList<GOTerm>();
				
				if(items.length > 1) {
					String[] terms = items[1].split("; ");
				
					Set<String> termSet = new HashSet<String>();
					
					for(String term : terms) {
						if(termSet.contains(term))
							continue;
						
						GOTerm goTerm = TermEmbedding.getTerm(term, type);
						if(goTerm != null)
							termList.add(goTerm);
						
						termSet.add(term);
					}
					
					termSet.clear();
				}
				
				proteinMap.put(items[0], termList);
				
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		return proteinMap;
	}
	
	private void outputTermCos(String outputFile) {
		StringBuffer sb = new StringBuffer();
		
		List<Map.Entry<String, Map<String, Double>>> list = BasicProcess.sortKeys(termCosMap);
		for(Map.Entry<String, Map<String, Double>> entry : list) {
			String termOneId = entry.getKey().replaceAll("GO:", "");
			
			Map<String, Double> subMap = entry.getValue();
			
			List<Map.Entry<String, Double>> subList = BasicProcess.sortKeys(subMap);
			for(Map.Entry<String, Double> subEntry : subList) {
				String termTwoId = subEntry.getKey().replaceAll("GO:", "");
				
				double cos = subEntry.getValue();
				
				sb.append(termOneId + "\t" + termTwoId + "\t" + cos + Setting.NEWLINE);
			}
		}
		
		IOProcess.saveFile(outputFile, sb.toString());
		
	}
	
}
