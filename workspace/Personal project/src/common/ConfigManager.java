package common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ConfigManager {
	
	private int savePoint = 0;
	private Configuration config = null;
	
	private static final Object lock = new Object();
	
	private static ConfigManager _instance = null;
	
	private ConfigManager () 
	{	
		final String propFileName = "oAuthCodes.properties";
	
		final Properties props = new Properties();
		final InputStream is = getClass().getClassLoader().getResourceAsStream(propFileName);
		
		try {
			if (is != null) {
				props.load(is);
			} 
			else {
				throw new FileNotFoundException (String.format("%s %s %s", "Property File", propFileName, "Not found in classpath"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		savePoint = Integer.parseInt(props.getProperty("savePoint").trim());
		
		final String oAuthConsumerKey = props.getProperty("oAuthConsumerKey");
		final String oAuthConsumerSecret = props.getProperty("oAuthConsumerSecret");
		final String oAuthAccessToken = props.getProperty("oAuthAccessToken");
		final String oAuthAccessTokenSecret = props.getProperty("oAuthAccessTokenSecret");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(oAuthConsumerKey)
								.setOAuthConsumerSecret(oAuthConsumerSecret)
								.setOAuthAccessToken(oAuthAccessToken)
								.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
		config = cb.build();
	}
	
	public static ConfigManager getInstance() {
		ConfigManager r = _instance;
	    if (r == null) {
	        synchronized (lock) {    
	            r = _instance;       
	            if (r == null) {  
	                r = new ConfigManager();
	                _instance = r;
	            }}}
	    return r;
	}
	
	public Configuration getConfiguration () {
		return this.config;
	}
	
	public int getSavePoint () {
		return this.savePoint;
	}
}
