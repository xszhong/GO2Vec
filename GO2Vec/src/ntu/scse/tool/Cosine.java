package ntu.scse.tool;

import java.util.List;

import ntu.scse.struct.GOTerm;
import ntu.scse.struct.Protein;
import ntu.scse.util.BasicProcess;

public class Cosine {
	
	public static double cosine(GOTerm one, GOTerm two) {
		double cos = 0;
		
		List<Double> embedListOne = one.getEmbedding();
		List<Double> embedListTwo = two.getEmbedding();
		
		if(embedListOne.size() != embedListTwo.size()) {
			System.out.println("The size the embedding vectors are not the same.");
			return cos;
		}
		
		double innerProduct = 0;
		for(int i = 0; i < embedListOne.size(); i++) {
			innerProduct += embedListOne.get(i) * embedListTwo.get(i);
		}
		
		double normValueOne = one.getEmdgNormValue();
		double normValueTwo = two.getEmdgNormValue();
		
		cos = innerProduct / (normValueOne * normValueTwo);
		
		return BasicProcess.validDigit(cos);
	}
	
	public static double cosine(Protein one, Protein two) {
		double cos = 0;
		
		List<Double> emdListOne = one.getEmd();
		List<Double> emdListTwo = two.getEmd();
		
		double normValueOne = one.getEmdNormValue();
		double normValueTwo = two.getEmdNormValue();
		
		if(normValueOne == 0 || normValueTwo == 0)
			return cos;
		
		if(emdListOne.size() != emdListTwo.size()) {
			System.out.println("The size the embedding vectors are not the same.");
			return cos;
		}
		
		double innerProduct = 0;
		for(int i = 0; i < emdListOne.size(); i++) {
			innerProduct += emdListOne.get(i) * emdListTwo.get(i);
		}
		
		cos = innerProduct / (Math.sqrt(normValueOne) * Math.sqrt(normValueTwo) );
		
		return BasicProcess.validDigit(cos);
		
	}
}
