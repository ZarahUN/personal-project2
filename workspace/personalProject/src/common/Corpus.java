package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Corpus 
{
	private String race;
	private String[] keywords;
	private SortedSet<Phrase> phrases = new TreeSet<Phrase>();

	public Corpus (CorpusTypes corpusType) {
		race = corpusType.keyword();
		loadCorpus(corpusType.corpus());
	}
	
	public Corpus (String filename) {
		loadCorpus(filename);
	}
	
	private void loadCorpus (String fileName) 
	{
		String usrDir = System.getProperty("user.dir");
		String file = String.format("%s\\%s\\%s", usrDir, "corpus", fileName);

		System.out.println("Loading in corpus : " + file);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();

			while (line != null) {
				if (line.contains("#RACE")) {
					setRace (line);
					line = br.readLine();
					continue;
				}
			
//				line = line.trim().toLowerCase();
//				phrases.add(new Phrase(line, race));
//				
//				if (line.contains("-")) { 
//					line = line.replace("-"," ");
//					phrases.add(new Phrase(line, race)); 
//				}
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int i = 0;
		keywords = new String[phrases.size()];
		for (Phrase phrase : phrases) {
			keywords[i++] = phrase.getPhrase();
		}
	}
	
	private void setRace (String line) {
		String[] array = line.split(" ");
		race = array[1].trim().toLowerCase();
	}
	
	public String[] getKeywords () {
		return this.keywords;
	}
	
	public Phrase getPhrase (String phrase) {
		for (Phrase p : phrases) {
			if (p.equals(phrase)) {
			return p;
			}}
		return null;
	}
	
	public Set<Phrase> getPhrases () {
		return this.phrases;
	}	
	
	
	public void printPhrases () {
		for (String keyword : keywords) {
			System.out.println(keyword);
		}
	}
}
