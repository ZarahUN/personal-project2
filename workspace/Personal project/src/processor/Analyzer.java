package processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import common.FileManager;

public final class Analyzer {
	
	enum SortBy { KEY, VALUE };
	enum Race { ALL, ARAB, BLACK, WHITE, MUSLIMS };
	
	private String[] racialSlurs = null;

	private static final String commonPhrasesFile = "common-phrases.txt";
	
	private static Analyzer _instance = null;
	
	private static final String usrDir = System.getProperty("user.dir");
	private static final String filteredDataFile = String.format("%s/%s/%s", usrDir,"sample-data","filteredData.out");
	
	private String[] keywords = null;
	
	private final HashMap<String, Integer> keywordsMap = new HashMap<String, Integer> ();
	private final HashMap<String, Integer> racialSlursMap = new HashMap<String, Integer> ();
	
	private Analyzer () {
		keywords = FileManager.getInstance().loadKeywords("keywords.txt");
		System.out.println("Loaded in keywords file: " + keywords.length);
		for (String keyword : keywords) {
			keywordsMap.put(keyword, new Integer(0));
		}
		System.out.println("Initialized Analyzer");
	}
	
	public static Analyzer getInstance() {
		if (_instance == null) {
			_instance = new Analyzer();
		}
		return _instance;
	}
	
	public void analyzeKeywords () {
		System.out.println("Analyzing keyword corpus: Number of elements: " + keywords.length);
		try {
			final File file = new File(filteredDataFile);
			final FileReader fileReader = new FileReader(file);
			final BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				processKeyWords(line);
			}
			fileReader.close();
			FileManager.getInstance().close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void analyzeCommonPhrases () {
		System.out.println("Analyzing Common Phrases");
		racialSlurs = FileManager.getInstance().loadKeywords(commonPhrasesFile);
		
		System.out.println("Loaded in Common Phrases: " + racialSlurs.length);
		for (String keyword : racialSlurs) {
			racialSlursMap.put(keyword, new Integer(0));
		}
		
		try {
			final File file = new File(filteredDataFile);
			final FileReader fileReader = new FileReader(file);
			final BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				processRacialSlurs(line);
			}
			fileReader.close();
			FileManager.getInstance().close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		System.out.printf("Analyzed Common Phrases: Lines Processed: %s Keywords Found: %s\n",lineCounter,keywordCounter);
	}
	
	public void analyzeRacialPhrases (String dataFile) {
		lineCounter = 0;
		System.out.println("Analyzing Racial Phrases: " + dataFile);
		
		racialSlurs = FileManager.getInstance().loadDataFile(dataFile);
		
		System.out.println("Loaded in data file: " + racialSlurs.length);
		for (String keyword : racialSlurs) {
			racialSlursMap.put(keyword, new Integer(0));
		}
		
		try {
			final File file = new File(filteredDataFile);
			final FileReader fileReader = new FileReader(file);
			final BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				processRacialSlurs(line);
			}
			fileReader.close();
			FileManager.getInstance().close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		System.out.printf("Analyzed Data File: Lines Processed: %-7d Keywords Found: %-7d\n",lineCounter,keywordCounter);
	}
	private void processKeyWords (String line) {
		for (String keyword : keywords) {
			if (line.contains(keyword)) {
				Integer value = keywordsMap.get(keyword) + 1;
				keywordsMap.replace(keyword, value);
			}}
	}

	private int lineCounter = 0;
	private int keywordCounter = 0;
	private void processRacialSlurs (String line) {
		lineCounter += 1;
		for (String keyword : racialSlurs) {
			if (line.contains(keyword)) { 
				keywordCounter += 1;
				Integer value = racialSlursMap.get(keyword) + 1;
				racialSlursMap.replace(keyword, value);
			}}
	}
	
	public void displayKeywords (SortBy sortBy) {
		if (sortBy == SortBy.KEY) sortByKey (keywordsMap);
		if (sortBy == SortBy.VALUE) sortByValue (keywordsMap);
	}

	public void displayRatialSlurs (SortBy sortBy) {
		if (sortBy == SortBy.KEY) sortByKey (racialSlursMap);
		if (sortBy == SortBy.VALUE) sortByValue (racialSlursMap);
	}
	
	private void sortByKey (HashMap<String, Integer> map) {
		Map<String, Integer> treeMap = new TreeMap<String, Integer>(map);
		for (Entry<String, Integer> entry : treeMap.entrySet()) {
		    System.out.printf("%s,%d\n", entry.getKey(), entry.getValue());
		}
		System.out.println();
	}
	
	private void sortByValue (HashMap<String, Integer> map) {
		Set<Entry<String, Integer>> set = map.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        });
        
        for(Map.Entry<String, Integer> entry:list){
        	System.out.printf("%s,%d\n", entry.getKey(), entry.getValue());
        }
        System.out.println();
	}
	
	public void printReport (String reportName) 
	{		
		String rptFile = String.format("%s\\%s\\%s.txt", usrDir, "reports", reportName);
		File file = new File (rptFile);
		
		try {
			PrintWriter writer = new PrintWriter(file);
			
			HashMap<String, Integer> map = new HashMap<String, Integer> ();
			if (reportName.equalsIgnoreCase("keywords")) {
				map = this.keywordsMap;
			}
			else {
				map = this.racialSlursMap;
			}
			
			Map<String, Integer> treeMap = new TreeMap<String, Integer>(map);
			for (Entry<String, Integer> entry : treeMap.entrySet()) {
				String line = String.format("%s,%s", entry.getKey(), entry.getValue());
				writer.println(line);
			}
			
			writer.flush();
			writer.close();
			System.out.printf("Created Report %-25s : Number of elements found: %d\n",reportName, map.size());
			System.out.println();
			map.clear();			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void processWord (String race, String phrase) {
		try {
			final File file = new File(filteredDataFile);
			final FileReader fileReader = new FileReader(file);
			final BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String filename = String.format("%s-%s.txt", race,phrase);
			String rptFile = String.format("%s\\%s\\%s", usrDir, "word-filters", filename);
			File rptfile = new File (rptFile);
			
			PrintWriter writer = new PrintWriter(rptfile);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] words = line.split(" ");
				for (String word : words) {
					if (word.equalsIgnoreCase(phrase)) {
						writer.println(line);
						break;
				}}}
			
			writer.flush();
			writer.close();
			bufferedReader.close();
			System.out.println("Created report file: " + filename);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
