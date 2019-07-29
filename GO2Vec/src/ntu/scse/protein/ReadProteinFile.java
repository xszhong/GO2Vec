package ntu.scse.protein;

import java.io.BufferedReader;
import java.io.IOException;

import ntu.scse.util.IOProcess;
import ntu.scse.util.Setting;

public class ReadProteinFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadProteinFile read = new ReadProteinFile();
		
		String inputFile = "resources/protein/protein-name-list.csv";
		String outputFile = "resources/protein/protein.goid";
		
		read.readProtein(inputFile, outputFile);

	}
	
	public void readProtein(String inputFile, String outputFile) {
		BufferedReader br = IOProcess.newReader(inputFile);
		String line;
		
		StringBuffer sb = new StringBuffer();
		try {
			while((line = br.readLine()) != null) {
				String[] items = line.split("\t");
				
				if(items.length < 6) {
					System.out.println(line);
					continue;
				}
				
				sb.append(items[0] + "\t" + items[4] + Setting.NEWLINE);
				
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { }
		
		IOProcess.saveFile(outputFile, sb.toString());
	}

}
