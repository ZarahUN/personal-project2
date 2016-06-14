package common;

public final class CorpusBuilder {
	
	public static void main(String[] args) {
		Corpus arabs = new Corpus (CorpusTypes.ARAB);
		Corpus blacks = new Corpus (CorpusTypes.BlACK);
		
//		Corpus arabs = new Corpus ("arabs.raw");
		//Corpus blacks = new Corpus ("blacks.raw");
		arabs.printPhrases();
		blacks.printPhrases();
	}
	
}
