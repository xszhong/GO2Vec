package ntu.scse.struct;

public class Edge {
	private String source;
	private String target;
	private String relation;
	
	private String nameSpace;
	
	public Edge(String source, String target) {
		this.source = source;
		this.target = target;
		this.relation = CONST.IS_A;
	}
	
	public Edge(String source, String target, String relation) {
		this(source, target);
		this.relation = relation;
	}
	
	public Edge(String source, String target, String relation, String nameSpace) {
		this(source, target, relation);
		this.nameSpace = nameSpace;
	}
	
	public String getSourceNode() {
		return source;
	}
	
	public String getTargetNode() {
		return target;
	}
	
	public String getRelation() {
		return relation;
	}
	
	public String getNameSpace() {
		return nameSpace;
	}

}
