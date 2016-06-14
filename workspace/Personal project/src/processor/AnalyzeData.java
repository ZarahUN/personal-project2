package processor;

import java.io.File;
import java.io.FileNotFoundException;

import common.FileManager;

public final class AnalyzeData {
	
	private static final String usrDir = System.getProperty("user.dir");
	private static final String dataDir = String.format("%s\\%s", usrDir,"corpus\\datafiles");
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Analyzer.getInstance().analyzeKeywords();
		Analyzer.getInstance().printReport("keywords");
		
		final File folder = new File(dataDir);
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	String dataFile = fileEntry.getName().split("\\.")[0];
	        	Analyzer.getInstance().analyzeRacialPhrases(dataFile);
	        	Analyzer.getInstance().printReport(dataFile);
	        }}	
	    
	    FileManager.getInstance().shutdown();
	}

}
