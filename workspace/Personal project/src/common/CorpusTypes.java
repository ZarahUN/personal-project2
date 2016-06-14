package common;

public enum CorpusTypes 
{
	ARAB ("arab","arabs.raw"),
	BlACK ("black","blacks.raw");
	
	CorpusTypes (String keyword, String filename) {
		this.keyword = keyword;
		this.filename = filename;
	}
	
	private final String filename;
	private final String keyword;
	
	public String keyword () {
		return this.keyword;
	}
	
	public String corpus () {
		return this.filename;
	}
	
}
