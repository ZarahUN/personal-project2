package filters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public final class FilterRCDB {

	private static String inFileName = null;
	private static String outFileName = null;
	
	
	private static String rawDataFile = null;
	private static String processedDataFile = null;
	
	public static void main(String[] args) {
		if (args.length != 0) 
			rawDataFile = args[0];
		else
			rawDataFile = "east-indians.txt";
		
		processedDataFile = "east-indians.txt";
		
		inFileName = String.format("%s\\%s\\raw-data\\%s",System.getProperty("user.dir"),"corpus",rawDataFile);
		outFileName = String.format("%s\\%s\\%s",System.getProperty("user.dir"),"corpus",processedDataFile);
		
		process ();
	}
	
	@SuppressWarnings("resource")
	private static void process () {
		File infile = new File (inFileName);
		File processedfile = new File (outFileName);
		
		System.out.println("Reading in file " + infile);
		System.out.println("Writing to file " + processedfile);
		
		SortedSet<String> set = new TreeSet<String>();
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(infile));
			String line; 
			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				set.add(line);
				if (line.contains("-"))
					set.add(line.replace("-", " "));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (!processedfile.exists()) {
				processedfile.delete();
			}
			
			processedfile.createNewFile();
			FileWriter fw = new FileWriter(processedfile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String s : set) {
				bw.write(s);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
