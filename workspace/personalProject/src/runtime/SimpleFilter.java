package runtime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import common.ConfigManager;
import common.Connection;
import common.FileManager;
import twitter4j.FilterQuery;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.User;

@SuppressWarnings("unused")
public class SimpleFilter 
{
	final static Logger logger = Logger.getLogger("Collectors");
	
	private static SimpleFilter _instance = null;

	private static StatusListener listener = null;
	
	private final Twitter twitterConnection = Connection.getInstance().getTwitterConnection();
	private final TwitterStream twitterStream = Connection.getInstance().getTwitterStream();
	
	private Date endTime = null;
	private Date startTime = new Date ();

	private SimpleFilter () {
		final String usrDir = System.getProperty("user.dir");
		final String log4jprops = String.format("%s\\%s\\%s.properties",usrDir,"properties","log4j");
		System.out.println(log4jprops);
		
		PropertyConfigurator.configure(log4jprops);
		
		logger.info("Started Tweets Collector @: " + startTime.toString());
	}
	
	public static SimpleFilter getInstance() {
		if (_instance == null) {
			_instance = new SimpleFilter();
		}
		return _instance;
	}

	public void collectTweets(String[] args) {
		
		logger.info(String.format("! Filtering Tweets by %s !",Arrays.toString(args)));
		
		setupStatusListener();
		twitterStream.addListener(listener);
		
		filterByKeywords(args);

	}

	private void filterByKeywords(String[] args) {
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.language(new String[] { "en" });
		
		twitterStream.filter(filterQuery);
		filterQuery.track(args[0]);
	}

	private void parseTweet(Status status) {
		Date now = new Date();
		if (now.before(endTime)) {
			if (!status.isRetweet()) {
				FileManager.getInstance().writeTweet(status);
			}}
		else {
			FileManager.getInstance().shutdown ();
			System.exit(0);
		}
	}

	private void setupStatusListener() {
		listener = new StatusListener() {

			@Override
			public void onException(Exception arg0) {
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
			}

			@Override
			public void onStatus(Status status) {
				parseTweet(status);
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
			}
			
		};
	}

	public void setEndTime (Date date) {
		this.endTime = date;
		logger.info("End Time set to " +  endTime.toString());	
	}
	
}
