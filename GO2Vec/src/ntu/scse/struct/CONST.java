package ntu.scse.struct;

public class CONST {
	public static final int _50DIMENSION = 50;
	public static final int _100DIMENSION = 100;
	public static final int _128DIMENSION = 128;
	
	public static final String BP = "biological-process";
	public static final String CC = "cellular-component";
	public static final String MF = "molecular-function";
	
	public static final String GO = "gene-ontology";
	
	public static final String BP_TermIdFile = "resources/go/term-id-biological-process.txt";
	public static final String CC_TermIdFile = "resources/go/term-id-cellular-component.txt";
	public static final String MF_TermIdFile = "resources/go/term-id-molecular-function.txt";
	
	public static final String GO_TermIdFile = "resources/go/term-id.txt";
	
//	public static final String BP_Term128DEmbeddingFile = "resources/go/embeddings/biological-process-extra-edge-128d.emd";
//	public static final String CC_Term128DEmbeddingFile = "resources/go/embeddings/cellular-component-extra-edge-128d.emd";
//	public static final String MF_Term128DEmbeddingFile = "resources/go/embeddings/molecular-function-extra-edge-128d.emd";
	
//	public static final String BP_Term128DEmbeddingFile = "resources/go/embeddings/term-graph-biological-process-weight-128d.emd";
//	public static final String CC_Term128DEmbeddingFile = "resources/go/embeddings/term-graph-cellular-component-weight-128d.emd";
//	public static final String MF_Term128DEmbeddingFile = "resources/go/embeddings/term-graph-molecular-function-weight-128d.emd";
	
	public static final String BP_Term100DEmbeddingFile = "resources/go/embeddings/term-graph-biological-process-100d.emb";
	public static final String CC_Term100DEmbeddingFile = "resources/go/embeddings/term-graph-cellular-component-100d.emb";
	public static final String MF_Term100DEmbeddingFile = "resources/go/embeddings/term-graph-molecular-function-100d.emb";
	
	public static final String BP_Term50DEmbeddingFile = "resources/go/embeddings/term-graph-biological-process-50d.emb";
	public static final String CC_Term50DEmbeddingFile = "resources/go/embeddings/term-graph-cellular-component-50d.emb";
	public static final String MF_Term50DEmbeddingFile = "resources/go/embeddings/term-graph-molecular-function-50d.emb";
	
	/**GO Annotations*/
	public static final String BP_Term128DEmbeddingFile = "resources/goa/embeddings/term-graph-ic-biological-process-128d.emd";
	public static final String CC_Term128DEmbeddingFile = "resources/goa/embeddings/term-graph-ic-cellular-component-128d.emd";
	public static final String MF_Term128DEmbeddingFile = "resources/goa/embeddings/term-graph-ic-molecular-function-128d.emd";
	
	public static final String GO_Term128DEmbeddingFile = "resources/go/embeddings/term-graph-128d.emd";
	
	/**
	 * is_a
	 * part_of
	 * regulates
	 * positively_regulates
	 * negatively_regulates
	 * */
	public static final String IS_A = "is_a";
	public static final String PART_OF = "port_of";
	public static final String REGULATES = "regulates";
	public static final String POSITIVE_REGULATES = "positive_regulates";
	public static final String NAGETIAVE_REGULATES = "negatively_regulates";
}
