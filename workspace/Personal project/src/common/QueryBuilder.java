package common;

import twitter4j.FilterQuery;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;

public class QueryBuilder {
	private static QueryBuilder _instance = null;
	
	private Twitter twitterConnection = null;
	private TwitterStream twitterStream = null;
	
	public static QueryBuilder getInstance() {
		if (_instance == null) {
			_instance = new QueryBuilder();
		}
		return _instance;
	}
	
	public void getByUserName(Status status) {
		getByUserName(status.getUser().getScreenName());
	}

	public void getSampleTweets() {
		FilterQuery tweetFilterQuery = new FilterQuery();
		tweetFilterQuery.locations(new double[][] { new double[] { -126.562500, 30.448674 }, new double[] { -61.171875, 44.087585 } });
		tweetFilterQuery.language(new String[] { "en" });

		twitterStream.filter(tweetFilterQuery);
		twitterStream.sample();
	}

	public void getByLocation() {
		FilterQuery tweetFilterQuery = new FilterQuery(); // See
		// tweetFilterQuery.track(new String[]{"Bieber", "Teletubbies"}); OR on keywords
		tweetFilterQuery.locations(
				new double[][] { new double[] { -126.562500, 30.448674 }, new double[] { -61.171875, 44.087585 } }); 
		// See https://dev.twitter.com/docs/streaming-apis/parameters#locations
		// for proper location doc.
		// Note that not all tweets have location metadata set.
		tweetFilterQuery.language(new String[] { "en" }); 

		twitterStream.filter(tweetFilterQuery);
	}

	public void getByUserName(String username) {
		int pageno = 1;

		while (true) {
			try {
				Paging page = new Paging(pageno++, 100);
				ResponseList<Status> responseList = twitterConnection.getUserTimeline(username, page);

				for (Status status : responseList) {
					System.out.println(status.getText());
				}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}
}
