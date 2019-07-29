package ntu.scse.go;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import ntu.scse.struct.CONST;
import ntu.scse.struct.Edge;
import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class InduceGOTermID {
	private HtmlCleaner cleaner;
	
	public InduceGOTermID() {
		cleaner = new HtmlCleaner();
	}
	
	public static void main(String[] args) {
		InduceGOTermID inducer = new InduceGOTermID();
		
		String inputGOFile = "resources/geneontology/go_daily-termdb.obo-xml";
		String outputDir = "resources/go/";
		
		inducer.induceTOTermID(inputGOFile, outputDir);
	}
	
	public void induceTOTermID(String inputGOFile, String outputDir) {
		outputDir = IOProcess.checkPath(outputDir);
		
		/**
		 * Map<NameSpace, List<String>>
		 * */
		Map<String, List<String>> termIdMap = new HashMap<String, List<String>>();
		
		/**
		 * Map<NameSpace, List<Edge>>
		 * */
		Map<String, List<Edge>> edgeMap = new HashMap<String, List<Edge>>();
		
		
		List<String> goTermIdList = new ArrayList<String>();
		List<Edge> goEdgeList = new ArrayList<Edge>();
		
		try {
			TagNode root = cleaner.clean(new File(inputGOFile));
			
			Object[] termObjs = root.evaluateXPath("//obo//term");
			
			int termSize = termObjs.length;
			
			System.out.println("Term size: " + termSize);
			
			for(Object termObj : termObjs) {
				TagNode termNode = (TagNode) termObj;
				
				/**
				 * GO term ID
				 * */
				String goTermID = ((TagNode)termNode.evaluateXPath("//id")[0]).getText().toString();
				
				goTermIdList.add(goTermID);
				
				/**
				 * Name space
				 * 
				 * One of the three values: cellular_component (CC), molecular_function (MF), biological_process (BP)
				 * */
				String nameSpace = ((TagNode)termNode.evaluateXPath("//namespace")[0]).getText().toString();
				
				if(! termIdMap.containsKey(nameSpace))
					termIdMap.put(nameSpace, new ArrayList<String>());
				termIdMap.get(nameSpace).add(goTermID);
				
				
				if(! edgeMap.containsKey(nameSpace))
					edgeMap.put(nameSpace, new ArrayList<Edge>());
				
				
				/**
				 * is_a relation
				 * */
				Object[] isAObjs = termNode.evaluateXPath("//is_a");
				if(isAObjs != null) {
					for(Object isAObj : isAObjs) {
						TagNode isANode = (TagNode) isAObj;
						String isATermID = isANode.getText().toString();
						//System.out.println("is_a: " + isANode.getText().toString());
						
						edgeMap.get(nameSpace).add(new Edge(goTermID, isATermID, CONST.IS_A, nameSpace));
						
						goEdgeList.add(new Edge(goTermID, isATermID, CONST.IS_A, nameSpace));
					}
				}
				
				Object[] relationshipObjs = termNode.evaluateXPath("//relationship");
				if(relationshipObjs != null) {
					for(Object relationshipObj : relationshipObjs) {
						TagNode relationshipNode = (TagNode) relationshipObj;
						
						String type = ((TagNode) relationshipNode.getElementsByName("type", false)[0]).getText().toString();
						
						String toTermID = ((TagNode) relationshipNode.getElementsByName("to", false)[0]).getText().toString();
						
						edgeMap.get(nameSpace).add(new Edge(goTermID, toTermID, type, nameSpace));
						
						goEdgeList.add(new Edge(goTermID, toTermID, type, nameSpace));
					}
				}
			}
			
		}  catch (IOException | XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		/**
		 * GO Term ID
		 * */
		for(String nameSpace : termIdMap.keySet()) {
			StringBuffer termSb = new StringBuffer();
			int index = 1;
			
			List<String> termList = termIdMap.get(nameSpace);
			for(String term : termList) {
				termSb.append(term + "\t" + nameSpace + "\t" + index + Setting.NEWLINE);
				index ++;
			}
			
			IOProcess.saveFile(outputDir + "term-id-" + nameSpace.replaceAll("_", "-") + ".txt", termSb.toString());
		} 
		
		StringBuffer termSb = new StringBuffer();
		int index = 1;
		for(String termId : goTermIdList) {
			termSb.append(termId + "\t" + index + Setting.NEWLINE);
			index ++;
		}
		IOProcess.saveFile(outputDir + "term-id.txt", termSb.toString());
		
		
		/**
		 * GO Term Graph
		 * */
		for(String nameSpace : edgeMap.keySet()) {
			List<Edge> edgeList = edgeMap.get(nameSpace);
			
			/**
			 * Map<String, Integer>
			 * */
			Map<String, Integer> relationMap = new HashMap<String, Integer>();
			
			StringBuffer edgeSb = new StringBuffer();
			for(Edge edge : edgeList) {
				String sourceNode = edge.getSourceNode();
				String targetNode = edge.getTargetNode();
				String relation = edge.getRelation();
				String space = edge.getNameSpace();
				
				edgeSb.append(sourceNode + "\t" + targetNode + "\t" + relation + "\t" + space + Setting.NEWLINE);
				
				int count = 1;
				if(relationMap.containsKey(relation))
					count += relationMap.get(relation);
				relationMap.put(relation, count);
			}
			
			IOProcess.saveFile(outputDir + "term-graph-" + nameSpace.replaceAll("_", "-") + ".txt", edgeSb.toString());
			
			System.out.println(nameSpace + "-------------------------");
			for(String relation : relationMap.keySet()) {
				System.out.println(relation + "\t" + relationMap.get(relation));
			}
		}
		
		StringBuffer edgeSb = new StringBuffer();
		for(Edge edge : goEdgeList) {
			String sourceNode = edge.getSourceNode();
			String targetNode = edge.getTargetNode();
			String relation = edge.getRelation();
			String space = edge.getNameSpace();
			
			edgeSb.append(sourceNode + "\t" + targetNode + "\t" + relation + "\t" + space + Setting.NEWLINE);
		}
		
		IOProcess.saveFile(outputDir + "term-graph.txt", edgeSb.toString());		
	}

}
