package runtime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import common.DayOfWeek;
import common.Months;

public class Controller {
	
	private static final String cfgFile = "config.properties";
	
	private static final String[] filter = {"a","e","i","o","u","y"};

	private Controller () {
		Properties props = new Properties();		
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(cfgFile);
			if (inputStream != null) {
				props.load(inputStream);
			}
			else {
				System.err.println("! error reading in properties file");
				System.exit(0);
			}}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		int DayOfMonth = Integer.parseInt(props.getProperty("DayOfMonth"));
		int HourOfDay = Integer.parseInt(props.getProperty("HourOfDay"));
		int Year = Integer.parseInt(props.getProperty("Year"));
		
		Months Month = Months.valueOf(props.getProperty("Month"));
		DayOfWeek dow = DayOfWeek.valueOf(props.getProperty("DayOfWeek"));

		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, Year);
		cal.set(Calendar.MONTH, Month.Value());
		cal.set(Calendar.DAY_OF_WEEK,dow.Value());
		cal.set(Calendar.DAY_OF_MONTH,DayOfMonth); 
		cal.set(Calendar.HOUR_OF_DAY, HourOfDay);
		
		SimpleFilter.getInstance().setEndTime(cal.getTime());
	}
	
	public static void main (String[] args) {
		new Controller();
		SimpleFilter.getInstance().collectTweets(filter);
	}
}
