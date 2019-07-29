package ntu.scse.struct;

import java.util.List;

import ntu.scse.util.BasicProcess;

public class Protein {
	private String proteinId;
	
	private List<String> termList;
	
	private List<Double> emd;
	private double emdNormValue;
	
	public Protein(String proteinId) {
		this.proteinId = proteinId;
	}
	
	public Protein(String proteinId, List<String> termList) {
		this(proteinId);
		this.termList = termList;
	}
	
	private void compteEmdNormValue() {
		for(Double element : emd)
			emdNormValue += element * element;
		
		emdNormValue = BasicProcess.validDecimal(emdNormValue);
	}
	
	public String getProteinId() {
		return proteinId;
	}
	
	public List<String> getTermList(){
		return termList;
	}
	
	public void setTermList(List<String> termList) {
		this.termList = termList;
	}
	
	public List<Double> getEmd() {
		return emd;
	}
	
	public void setEmd(List<Double> emd) {
		this.emd = emd;
		compteEmdNormValue();
	}
	
	public double getEmdNormValue() {
		return emdNormValue;
	}
	
}
