package utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import common.Phrase;
import common.Races;

public class Utils {

	private static SortedSet<Phrase> phrases = new TreeSet<Phrase> ();
	
	public final static String filterWord (String word) {
		word = word.toLowerCase();
		word = word.replaceAll("!","");
		word = word.replaceAll("\\?","");
		word = word.replaceAll("\"","");
		word = word.replaceAll("'","");
		word = word.replaceAll("\\.","");
		
		return word;
	}
	
	public final static String getDatetimeFileExtension (String dir, String filename) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date ());
		
		return String.format("%s\\%s.%02d-%02d-%d [%03d.%03d].txt"
							, dir
							, filename
							, cal.get(Calendar.MONTH)+1
							, cal.get(Calendar.DAY_OF_MONTH)
							, cal.get(Calendar.YEAR)
							, cal.get(Calendar.HOUR)
							, cal.get(Calendar.MINUTE)
							);
	}
	
	public final static String getDateFileExtension (String dir, String filename) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date ());
		
		return String.format("%s\\%s.%02d-%02d-%d.txt"
							, dir
							, filename
							, cal.get(Calendar.MONTH)
							, cal.get(Calendar.DAY_OF_MONTH)
							, cal.get(Calendar.YEAR));
	}
	
	public static String rptDir = null;
	
	public final static void printReport (Map<String,Integer> map, String filename) {
		String rptFile = getDatetimeFileExtension (rptDir, filename );
		try {
			final FileWriter rptWriter = new FileWriter (rptFile);
			for(Map.Entry<String,Integer> entry : map.entrySet()) {
				rptWriter.write(String.format("%s,%d\n",entry.getKey(), entry.getValue()));
				if (entry.getValue() != 0) {
					System.out.printf("%-35s %5d\n", entry.getKey().toString(), entry.getValue());
				}}
			rptWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.printf ("! Report File %25s written\n\n",rptFile);
	}
	
	public final static void printReports (Map<Races,Integer> map, String filename) {
		String rptFile = getDatetimeFileExtension (rptDir, filename );
		try {
			final FileWriter rptWriter = new FileWriter (rptFile);
			for(Map.Entry<Races,Integer> entry : map.entrySet()) {
				rptWriter.write(String.format("%s,%d\n",entry.getKey(), entry.getValue()));
				if (entry.getValue() != 0) {
					System.out.printf("%-35s %-53d\n", entry.getKey().toString(), entry.getValue());
					rptWriter.write(String.format("%s,%s",entry.getKey().toString(), entry.getValue()));
					rptWriter.write("\n");
				}}
			rptWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.printf ("! Report File %25s written\n\n",rptFile);
	}

	public static final String[] getEthnicPhrases (File file) {

		SortedSet<Phrase> set = new TreeSet<Phrase> ();		
		try {
			final FileReader fileReader = new FileReader(file);
			final BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] linebreak = line.split("\t", 2);
				if (!linebreak[0].equalsIgnoreCase("K")) 
					continue;
				
				Phrase phrase = new Phrase(linebreak[1]);
				set.add(phrase);
				phrases.add(phrase);
				if (phrase.isHyphenated()) {
					set.add(new Phrase (phrase.removeHyphen(), phrase.getClassification(), phrase.getDescription()));
					phrases.add(new Phrase (phrase.removeHyphen(), phrase.getClassification(), phrase.getDescription()));
				}}
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int i = 0;
		String[] a = new String[set.size()];
		for (Phrase p : set) {
			a[i++] = p.getComparator();
		}

		System.out.printf("Total Phrases For Ethnic Group: %-15s : %05d\n", file.getName().replace(".raw","").toUpperCase(), a.length);
		return a;
	}

	public final static Phrase getEthnicPhrase (String phrase) {
		for (Phrase p : phrases) {
			if (p.equals(phrase))
				return p;
		}
		return null;
	}
	
	//1 minute = 60 seconds
	//1 hour = 60 x 60 = 3600
	//1 day = 3600 x 24 = 86400
	public final static long calculateElapsedTime(Date startDate, Date endDate){
		return (endDate.getTime() - startDate.getTime())/1000;
	}

	public final static void displayElapsedTime(Date startDate, Date endDate){
		System.out.printf("! %s\n",getElapsedTime (startDate, endDate));
	}
	
	public final static String getElapsedTime (Date startDate, Date endDate) {
		long different = endDate.getTime() - startDate.getTime();
		
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;

		System.out.println("! Start Date            : " + startDate);
		System.out.println("! End Date              : " + endDate);
		System.out.println("! Elapsed Time [Millsec]: " + different);
		
		return String.format("Total Elapsed Time : %02d days, %02d hours, %02d minutes, %02d seconds", elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
	}
}
