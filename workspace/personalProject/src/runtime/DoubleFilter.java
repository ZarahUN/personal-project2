package runtime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
public class DoubleFilter 
{
	// Ottawa 45.4215° N, 75.6972° W
	private static int tweetcounter = 0;
	private static int racialcounter = 0;
	private static int hateSpeechCounter = 0;

	private static final int MAX_TWEETS = 1048577;
	
	private static String[] keywords = FileManager.getInstance().loadKeywords("keywords.txt");
	private static String[] racialSlurs = FileManager.getInstance().loadKeywords("racialslurs.txt");

	private static StatusListener listener = null;
	private static final Twitter twitterConnection = Connection.getInstance().getTwitterConnection();
	private static final TwitterStream twitterStream = Connection.getInstance().getTwitterStream();
	
	private static Date endTime = null;
	private static Date startTime = new Date ();
	
	private static final int CheckPoint = 6; // Number of hours between saving files
	private static final int Year = 2016;
	private static final int Month = Calendar.APRIL;
	private static final int WeekDay = Calendar.WEDNESDAY;
	private static final int Day = 20;
	private static final int Hour = 12;
		
	public static void main(String[] args) {
		
		setEndDate();
		
		setupStatusListener();
		twitterStream.addListener(listener);

		filterByKeywords(keywords);
		
	}

	private static void filterByKeywords(String[] args) {
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.language(new String[] { "en" });
		
		twitterStream.filter(filterQuery);
		filterQuery.track(keywords);
	}

	private static void parseTweet(Status status) {
		tweetcounter += 1;
		if (!status.isRetweet()) {
			hateSpeechCounter += 1;
			//FileManager.getInstance().getCheckPoint(CheckPoint);
			FileManager.getInstance().writeHateSpeechTweet(status);
			if (containsSlur (status.getText())) { 
				racialcounter += 1;
				writeTweet (status);
			}
			System.out.println(String.format("[%-12d][%-7d][%-7d]", tweetcounter, hateSpeechCounter, racialcounter));
		}
	}

	// set to true if you only want the tweet to contain the exact word only
	// false is you are looking for words that may contain part of the phrase
	// e.g. kkkracker or kracker
	private static boolean containsSlur(String tweet) {
		for (String slur : racialSlurs) {
			if (tweet.toLowerCase().contains(slur)) {
				return true;
		}}
		return false;
	}

	private static void clearAll() {
		twitterStream.clearListeners();
	}

	private static void writeTweet(Status status)
	{		
		Date now = new Date();
		if (now.before(endTime)) {
			FileManager.getInstance().writeTweet(status);
		}
		else {
			System.out.println("Total Elapsed Time: " + (endTime.getTime() - startTime.getTime()));
			FileManager.getInstance().close ();
			clearAll();
			System.exit(0);
		}
	}
	
	private static void setupStatusListener() {
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

	private static void setEndDate () {
		//Calendar cal = Calendar.getInstance();
		
		Calendar date = new GregorianCalendar(Year, Month, Day);		
		date.set(Calendar.HOUR_OF_DAY,Hour);
		endTime = date.getTime();
		
		System.out.println("End Time set to " +  endTime.toString());		
	}
	
}
