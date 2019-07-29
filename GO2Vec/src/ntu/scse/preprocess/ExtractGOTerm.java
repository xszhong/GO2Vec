package ntu.scse.preprocess;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import ntu.scse.util.BasicProcess;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class ExtractGOTerm {
	
	private HtmlCleaner cleaner;
	
	public ExtractGOTerm() {
		cleaner = new HtmlCleaner();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputFile = "resources/geneontology/go_daily-termdb.obo-xml";
		String outputDir = "resources/geneontology/geneterm/";
		
		ExtractGOTerm extractor = new ExtractGOTerm();
		
		extractor.extractGOTerm(inputFile, outputDir);
	}
	
	public void extractGOTerm(String inputFile, String outputDir) {
		outputDir = IOProcess.checkPath(outputDir);
		
		Set<String> termIdSet = new HashSet<String>();
		StringBuffer edgeSb = new StringBuffer();
		
		try {
			TagNode root = cleaner.clean(new File(inputFile));
			
			Object[] termObjs = root.evaluateXPath("//obo//term");
			
			int size = termObjs.length;
			
			System.out.println("Term size: " + size);
			
			for(Object termObj : termObjs) {
				TagNode termNode = (TagNode) termObj;
				
				/**
				 * get GO term ID
				 * */
				String id = ((TagNode)termNode.evaluateXPath("//id")[0]).getText().toString();
				termIdSet.add(id);
				
//				System.out.println("id: " + id);
				
				/**
				 * is_a
				 * */
				Object[] isAObjs = termNode.evaluateXPath("//is_a");
				if(isAObjs != null) {
					for(Object isAObj : isAObjs) {
						TagNode isANode = (TagNode) isAObj;
						String isAId = isANode.getText().toString();
						//System.out.println("is_a: " + isANode.getText().toString());
						
						termIdSet.addAll(termIdSet);
						
						edgeSb.append(id + "\t" + isAId + Setting.NEWLINE);
					}
				}
				
				Object[] relationToObjs = termNode.evaluateXPath("//relationship//to");
				if(relationToObjs != null) {
					for(Object relationToObj : relationToObjs) {
						TagNode relationToNode = (TagNode) relationToObj;
						
						String relationToId = relationToNode.getText().toString();
						
						termIdSet.add(relationToId);
						
						edgeSb.append(id + "\t" + relationToId + Setting.NEWLINE);
					}
				}
			}
			
		} catch (IOException | XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		List<String> termIdList = BasicProcess.sortSet(termIdSet);
		StringBuffer idSb = new StringBuffer();
		for(int i = 1; i <= termIdList.size(); i++) {
			idSb.append(termIdList.get(i - 1) + "\t" + i + Setting.NEWLINE);
		}
		
		IOProcess.saveFile(outputDir + "term-id.txt", idSb.toString());
		
		IOProcess.saveFile(outputDir + "term-graph.list", edgeSb.toString());
		
	}

}
