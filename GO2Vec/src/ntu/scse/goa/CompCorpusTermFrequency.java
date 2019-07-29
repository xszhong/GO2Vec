package ntu.scse.goa;

import java.io.BufferedReader;
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

public class CompCorpusTermFrequency {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String goType = CONST.MF;
		
		String inputGafFile = "resources/goa/corpus-goa/rawgaf/goa_human.gaf";
		String outputFile = "resources/goa/corpus-goa/termfreq/goa-human-term-freq-" + goType + ".txt";
		
		CompCorpusTermFrequency comp = new CompCorpusTermFrequency();
		comp.compCorpusTerm(inputGafFile, goType, outputFile);

	}
	
	public void compCorpusTerm(String inputGafFile, String goType, String outputFile) {
		
		Set<String> termIdSet = loadTerm("resources/go/term-id-" + goType + ".txt");
		
		BufferedReader br = IOProcess.newReader(inputGafFile);
		String line;
		
		/**
		 * Map<Protein, Set<Term>>
		 * */
		Map<String, Set<String>> proteinMap = new HashMap<String, Set<String>>();
		try {
			while((line = br.readLine()) != null) {
				if(line.startsWith("!"))
					continue;
				String[] items = line.split("(\t| )+");
				if(items.length < 5)
					continue;
				
				String protein = items[1];
				String term = items[3];
				if(! term.startsWith("GO:"))
					term = items[4];
				if(! term.startsWith("GO:"))
					continue;
				if(! termIdSet.contains(term))
					continue;
				
				/**
				 * UniProtKB	A0A024R161	DNAJC25-GNG10		GO:0003924
				 * */
				
				if(! proteinMap.containsKey(protein))
					proteinMap.put(protein, new HashSet<String>());
				
				proteinMap.get(protein).add(term);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		/**
		 * Map<Term, Count>
		 * */
		Map<String, Integer> termFreqMap = new HashMap<String, Integer>();
		for(String protein : proteinMap.keySet()) {
			Set<String> termSet = proteinMap.get(protein);
			
			for(String term : termSet) {
				int count = 1;
				if(termFreqMap.containsKey(term))
					count += termFreqMap.get(term);
				termFreqMap.put(term, Integer.valueOf(count));
			}
		}
		
		int totalProtein = proteinMap.size();
		
		List<Map.Entry<String, Integer>> termFreqList = BasicProcess.sortValues(termFreqMap, false);
		StringBuffer sb = new StringBuffer();
		sb.append("GOTerm\tCount\tFrequency\tIC" + Setting.NEWLINE);
		
		for(Map.Entry<String, Integer> entry : termFreqList) {
			String term = entry.getKey();
			int count = entry.getValue();
			double freq = count * 1.0 / totalProtein;
			double ic = - Math.log(freq);
			
			sb.append(term + "\t" + count + "\t" + BasicProcess.validDigit(freq) + "\t" + BasicProcess.validDigit(ic) + Setting.NEWLINE);
		}
		
		IOProcess.saveFile(outputFile, sb.toString());
		
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
