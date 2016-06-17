package utils;

import java.io.File;
import java.io.FileNotFoundException;

public final class TestCases {
	public static void main(String[] args) throws FileNotFoundException {
		String line = "adfaASADMEffucADFDFkadfadfmmmmmmdfadfa";
		System.out.println(line.toLowerCase().contains("me"));
		
		System.out.println(25%2);
		
		final String usrDir = System.getProperty("user.dir");
		final String sampleDataDir = String.format("%s/%s", usrDir,"sample-data");
		final File folder = new File(sampleDataDir);
	    for (final File fileEntry : folder.listFiles()) {	
	    	System.out.printf("%-35s %s\n",fileEntry.getName(),fileEntry.getName().contains("000"));
	    }
	}
}
