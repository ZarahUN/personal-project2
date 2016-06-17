package common;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import utils.Utils;

public class Phrase implements Comparable<Phrase> {
	
	@XmlAttribute (name ="ethnicity")
	private String classification;
	@XmlElement
	private String phrase;
	@XmlAttribute (name ="comparator")
	private String comparator;
	@XmlElement
	private String description;
	
	public Phrase (String input) {
		String[] array = input.split("\t");
		this.phrase = array[0];
		this.classification = array[1];
		this.description = array[2];
		this.comparator = Utils.filterWord(phrase);
	}
	
	public Phrase (String phrase, String classification, String description) {
		this.phrase = phrase;
		this.classification = classification;
		this.description = description;
		this.comparator = filterWord(phrase);
	}
	
	public String getPhrase() {
		return phrase;
	}

	public String getComparator() {
		return comparator;
	}
	
	public String getClassification() {
		return classification;
	}

	public String getDescription() {
		return description;
	}

	public boolean isHyphenated () {
		return comparator.contains("-");
	}
	
	public String removeHyphen () {
		return comparator.replaceAll("-", " ");
	}

	@Override
	public int compareTo(Phrase arg0) {
		return this.comparator.compareToIgnoreCase(arg0.getComparator());
	}
	
	public boolean equals (String phrase) {
		return this.comparator.equalsIgnoreCase(phrase);
	}
	
	public String toString () {
		return String.format("%s|%s|%s",this.classification,this.phrase,this.description);
	}
	
	private final String filterWord (String word) {
		word = word.toLowerCase();
		word = word.replaceAll("!","");
		word = word.replaceAll("\\?","");
		word = word.replaceAll("\"","");
		word = word.replaceAll("'","");
		word = word.replaceAll("\\.","");
		
		return word;
	}
}
