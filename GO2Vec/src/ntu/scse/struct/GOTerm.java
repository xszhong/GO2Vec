package ntu.scse.struct;

import java.util.List;

import ntu.scse.util.BasicProcess;

public class GOTerm {
	private String termId;
	private String numId;
	private String type;	// name space
	
	private List<Double> embedding;
	private double emdNormValue;
	
	public GOTerm(String termId, String numId, String type, List<Double> embedding) {
		this.termId = termId;
		this.numId = numId;
		this.type = type;
		this.embedding = embedding;
		
		compteEmdNormValue();
	}
	
	private void compteEmdNormValue() {
		for(Double element : embedding)
			emdNormValue += element * element;
		
		emdNormValue = BasicProcess.validDecimal(Math.sqrt(emdNormValue));
	}
	
	public String getTermId() {
		return termId;
	}
	
	public String getNumId() {
		return numId;
	}
	
	public String getType() {
		return type;
	}
	
	public List<Double> getEmbedding() {
		return embedding;
	}
	
	public double getEmdgNormValue() {
		return emdNormValue;
	}
	
}
