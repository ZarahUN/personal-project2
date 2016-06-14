package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.User;
import utils.Utils;

@SuppressWarnings("resource")
public final class FileManager {
	final static Logger logger = Logger.getLogger("Filters");
	
	final private int savePoint = ConfigManager.getInstance().getSavePoint();
	
	private static FileManager _instance = null;
	
	private int schedule = 0;
	private int tweetCounter = 0;
	private int metaDataCounter = 0;
	private int hateSpeechCounter = 0;
	
	private Thread timer = new SaveTask(); 
	
	private static final Date startTime = new Date();
	private static final String localDir = "corpus";
	private static final String tweetsFile = "tweets.out";
	private static final String metaDataFile = "metaData.out";
	private static final String hateSpeechFile = "hatespeech.out";
	
	private BufferedWriter tweetsWriter = null;
	private BufferedWriter metaDataWriter = null;
	private BufferedWriter hateSpeechWriter = null;
	
	boolean done = false;
		 
	private FileManager () 
	{	
		final String usrDir = System.getProperty("user.dir");
		final String log4jprops = String.format("%s\\%s\\%s.properties",usrDir,"properties","log4j");
		System.out.println(log4jprops);
		
		PropertyConfigurator.configure(log4jprops);
		
		logger.info("Started Tweets Collector @: " + startTime.toString());
		schedule = savePoint * 60 * 60 * 1000; 
		
		// Save a file every 10 mins
		schedule = 10 * 60 * 1000;
		timer.start();    
	}
	
	public static FileManager getInstance() {
		if (_instance == null) {
			_instance = new FileManager();
		}
		return _instance;
	}

	
	public String[] loadDataFile (String fileName) 
	{
		String usrDir = System.getProperty("user.dir");
		String file = String.format("%s\\corpus\\datafiles\\%s.txt", usrDir,fileName);

		SortedSet<String> set = new TreeSet<String>();
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				set.add(line);
				if (line.contains("-")) {
					set.add(line.replace("-", " "));
				}}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int i = 0;
		String[] array = new String[set.size()];
		for (String s : set) {
			array[i++] = s;
		}
		
		return array;
	}
	
	public String[] loadKeywords (String fileName) 
	{
		String usrDir = System.getProperty("user.dir");
		String file = String.format("%s\\%s\\%s", usrDir, localDir, fileName);

		SortedSet<String> set = new TreeSet<String>();
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				set.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int i = 0;
		String[] array = new String[set.size()];
		for (String s : set) {
			array[i++] = s;
		}
		
		return array;
	}
	
	public void writeKeywordsFile (String[] keywords, String fileName) 
	{
		String usrDir = System.getProperty("user.dir");
		String revFileName = String.format("%s\\%s\\%s", usrDir, "properties", fileName);
		
		File file = new File(revFileName);
		
		if (!file.exists()) {
			file.delete();
		}
		
		try {
			file.createNewFile();
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String s : keywords) {
				bw.write(s);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createOutputFile (String filename) 
	{
		System.out.println("Opening File: " + filename);
		String fileDir = "";
		
		if (filename.equalsIgnoreCase(tweetsFile))
			fileDir = "tweets";
		
		if (filename.equalsIgnoreCase(metaDataFile))
			fileDir = "meta-data";
		
		if (filename.equalsIgnoreCase(hateSpeechFile))
			fileDir = "raw-data";

		String usrDir = System.getProperty("user.dir");
		String outFile = String.format("%s\\%s\\%s", usrDir, fileDir, filename);
				
		File file = new File(outFile);
		
		if (file.exists()) {
			String oldFileName = String.format ("%s.%d",outFile, (new Date()).getTime());
			file.renameTo(new File (oldFileName));
		}
		
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			if (filename.equalsIgnoreCase(tweetsFile))
				tweetsWriter = new BufferedWriter(fw);
			if (filename.equalsIgnoreCase(metaDataFile))
				metaDataWriter = new BufferedWriter(fw);
			if (filename.equalsIgnoreCase(hateSpeechFile))
				hateSpeechWriter = new BufferedWriter(fw);						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close () 
	{
		try {
			if (tweetsWriter != null) {
				tweetsWriter.close();
				tweetsWriter = null;
				
				String tc = String.format("%9s",NumberFormat.getNumberInstance(Locale.US).format(tweetCounter));
				String msg = String.format("! Closing Tweets File! Tweets collected so far %s", tc);
				logger.info(msg);
			}
	
			if (metaDataWriter != null) {
				System.err.println(String.format("! Closing Meta Data File! Tweets collected so far %d", metaDataCounter));
				metaDataWriter.close();
				metaDataWriter = null;
			}
			
			if (hateSpeechWriter != null) {
				System.err.println(String.format("! Closing Hate Speech File! Tweets collected so far %d", hateSpeechCounter));
				hateSpeechWriter.close();
				hateSpeechWriter = null;
			}
			
            System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void shutdown () {
		this.close();
		this.tweetsWriter = null;
		this.metaDataWriter = null;
		this.hateSpeechWriter = null;
		
		Date endTime = new Date();
		this.done = true;

		System.out.println("! Closed Output files !");
		System.out.println("! Shutdown system !");
				
		logger.info("! Shutdown Tweets Collector @: " + endTime.toString());
		
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		String msg = String.format("! Closing Tweets File! Tweets collected so far %9s", formatter.format(tweetCounter));
		
		logger.info(msg);
		logger.info(String.format("! %s",Utils.getElapsedTime(startTime, endTime)));
		
        System.gc();
	}
	
	public void writeTweet (Status status) {
		if (tweetsWriter == null) createOutputFile (tweetsFile);
		
		try {
			tweetCounter += 1;
			tweetsWriter.write(status.getText().replaceAll("\\n",  " "));
			tweetsWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void writeHateSpeechTweet (Status status) 
	{	
		if (hateSpeechWriter == null) createOutputFile (hateSpeechFile);
		
		User user = status.getUser();
		String location = user.getLocation() == "" ? "Not Listed" : user.getLocation(); 
		String out = String.format ("%d,%s,%s,%s", status.getId(), user.getName(), location, status.getText().replaceAll("\\n",  " ")); 
		try {
			hateSpeechCounter += 1;
			hateSpeechWriter.write(out);
			hateSpeechWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void writeMetaData (Status status) 
	{	
		if (metaDataWriter == null) createOutputFile (metaDataFile);
		
		User user = status.getUser();
		String location = user.getLocation() == "" ? "Not Listed" : user.getLocation(); 
		String out = String.format ("%d,%s,%s,%s", status.getId(), user.getName(), location, status.getText().replaceAll("\\n",  " ")); 
		try {
			metaDataCounter += 1;
			metaDataWriter.write(out);
			metaDataWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings("unused")
	private String getHashTags (Status status) {
		HashtagEntity[] hashTags =  status.getHashtagEntities();
		
		String hTags = "";
		for (HashtagEntity hashtag : hashTags) {
			hTags += "#";
			hTags += hashtag.getText();
			hTags += " ";
		}
		
		return hTags;
	}
	
	private void saveFiles () {
		Date now = new Date ();
		System.err.println("! Saving Files : " + now.toString());
		close();
	}

	class SaveTask extends Thread   {
		public void run() {
			System.out.printf("! Timer started %2d %d\n", savePoint, schedule);
			while (!done) {
				try {
					Thread.sleep(schedule);
					saveFiles();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			timer = null;	
		}
    }
}
