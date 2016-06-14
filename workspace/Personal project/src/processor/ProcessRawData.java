package processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import filters.Filter;

@SuppressWarnings("unused")
public final class ProcessRawData {
 
	private static int outerCounter = 0;
	private static PrintWriter writer = null;
	
	private static final String sampleData = "sampleData.out";
	private static final String filteredData = "filteredData.out";
	
	private static final String usrDir = System.getProperty("user.dir");
	private static final String logsDir = String.format("%s/%s", usrDir,"logs");
	private static final String rawDataDir = String.format("%s/%s", usrDir,"raw-data");
	private static final String sampleDataDir = String.format("%s/%s", usrDir,"sample-data");

	public static void main (String[] args) {
		//combineFiles();
		//filterData();
		appendData();
		//displayFilteredData();
	}
	
	public static void combineFiles () {
		try {
			writer = new PrintWriter(String.format("%s/%s", logsDir, sampleData));
			
			final File folder = new File(rawDataDir);
		    for (final File fileEntry : folder.listFiles()) {
		        if (!fileEntry.isDirectory())
		            readFile(fileEntry);
		    }
		    
		    writer.flush();
		    writer.close();
		    System.out.println("Processed Tweets: " + outerCounter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readFile (File file) {
		int innerCounter = 0;
		try {
			System.err.println("Processing file : " + file.getAbsolutePath());
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line != null) {	
					outerCounter += 1;
					innerCounter += 1;
					writer.println(line);
				}}
			fileReader.close();
			fileReader = null;
			System.err.println("Processing tweets : " + innerCounter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void filterData() {	
		outerCounter = 0;
		try {
			writer = new PrintWriter(String.format("%s/%s", sampleDataDir, filteredData));
			
			processFile (new File (String.format("%s\\%s",sampleDataDir,sampleData)));
			
		    writer.flush();
		    writer.close();
		    System.out.println("Processed Tweets: " + outerCounter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void appendData() {
		int outercounter = 0;
		String filename = String.format("%s\\%s",sampleDataDir,filteredData);
		File file = new File (filename);
		
		try {
			if (!file.exists())	file.createNewFile();

			PrintWriter out = new PrintWriter(new FileOutputStream(file, true));
			final File folder = new File(sampleDataDir);
		    for (final File fileEntry : folder.listFiles()) {		        
		    	if (!fileEntry.isDirectory()) {
		    		if (fileEntry.getName().startsWith("metaData")) {
		    			int innerCounter = 0;
		    			System.out.println("Processing file: " + fileEntry.getAbsolutePath());
		            
		    			FileReader fileReader = new FileReader(fileEntry);
		    			BufferedReader bufferedReader = new BufferedReader(fileReader);

		    			String line;
		    			while ((line = bufferedReader.readLine()) != null) {
		    				if (line != null) {	
		    					innerCounter += 1;		    					
		    					String s = "";
		    					if (fileEntry.getName().contains("000")) {		    						
		    						s = getTweet (line, false);
		    					}
		    					else
		    						s = getTweet (line, true);
		    					
		    					if (s != null && s.length() != 0) {
		    						out.append(Filter.process(s) + "\n");
		    						outercounter += 1;
		    					}
		    				}}
		    			fileReader.close();
		    			bufferedReader.close();
		    			System.err.println("Processed tweets : " + innerCounter);
		    	}}}
		    out.flush();
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("Finshed processing file! " + outercounter);
	}
	
	private static void displayFilteredData () {
		int innerCounter = 0;
		
		String filename = String.format("%s\\%s",sampleDataDir,filteredData);
		File file = new File (filename);
		
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				innerCounter += 1;
				System.out.println(line);
			}		    		
			fileReader.close();
			bufferedReader.close();
			System.err.println("Total tweets : " + innerCounter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void processFile (File file) {
		try {
			System.err.println("Processing file : " + file.getAbsolutePath());
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String filteredLine =  Filter.process(line);
				if (filteredLine != null) {					
					if (filteredLine.length() != 0)
						outerCounter += 1;
						writer.println(filteredLine);
				}}
			fileReader.close();
			fileReader = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getTweet (String line, Boolean splitString) {
		
		if (line.length() != 0 && line != null) {
			if (splitString) {
				String[] splif = line.split(",");
				if (splif.length >= 3) {
					String s = "";
					for (int i = 3; i<splif.length;i++) {
						s += splif[i];
						s += " ";
					}
					s.trim();
					return Filter.process(s);
				}}
			else {
				return Filter.process(line);
			}
		}
		
		return null;
	}
}
