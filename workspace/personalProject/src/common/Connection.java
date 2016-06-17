package common;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

public final class Connection 
{	
	private static Connection _instance = null;
	
	private Twitter twitterConnection = null;
	private TwitterStream twitterStream = null;
	
	private Connection () 
	{		
		System.err.println("Opening Connection");
		Configuration config = ConfigManager.getInstance().getConfiguration(); //cb.build();
		
		TwitterFactory tf = new TwitterFactory(config);
		twitterConnection = tf.getInstance();
		twitterStream = new TwitterStreamFactory(config).getInstance();
		try {
			System.err.println(twitterConnection.getId());
		} catch (IllegalStateException | TwitterException e) {
			e.printStackTrace();
		} 
	}
		
	public static Connection getInstance() {
		if (_instance == null) {
			_instance = new Connection();
		}
		return _instance;
	}
	
	public Twitter getTwitterConnection () {
		return twitterConnection;
	}
	
	public TwitterStream getTwitterStream () {
		return twitterStream;
	}
	
	public void closeConnection () {
		twitterStream.cleanUp();
		twitterStream.clearListeners();
	}
}
