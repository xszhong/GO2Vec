package ntu.scse.struct;

import java.util.ArrayList;
import java.util.List;

public class TermEdge {
	private String termId;
	private List<Edge> edgeList;
	private List<Edge> extraEdgeList;
	
	public TermEdge(String termId) {
		this.termId = termId;
		
		edgeList = new ArrayList<Edge>();
		extraEdgeList = new ArrayList<Edge>();
	}
	
	public void addEdge(String targetId, String relation) {
		edgeList.add(new Edge(termId, targetId, relation));
	}
	
	public void addExtraEdge(String targetId, String relation) {
		extraEdgeList.add(new Edge(termId, targetId, relation));
	}
	
	public String getTermId() {
		return termId;
	}
	
	public List<Edge> getEdgeList(){
		return edgeList;
	}
	
	public List<Edge> getExtraEdgeList(){
		return extraEdgeList;
	}

}
