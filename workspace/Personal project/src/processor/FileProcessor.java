package processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedSet;
import java.util.TreeSet;

public final class FileProcessor {

	private static final String usrDir = System.getProperty("user.dir");
	private static final String outFile = "complete-list-ratial-slurs.txt";
	private static final String dataDir = String.format("%s\\%s\\%s", usrDir, "corpus", "raw-data");
	
	private static SortedSet<String> set = new TreeSet<String>();
	
	public static void main(String[] args) {
		readFiles ();
		writeFile ();
	}
	
	private static void readFiles () {
		final File folder = new File(dataDir);
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory())
	            readFile(fileEntry);
	    }
	}

	private static void readFile (File file) {
		try {
			System.err.println("Processing file : " + file.getAbsolutePath());
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				filter(line);
			}	
			
			fileReader.close();
			fileReader = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void filter (String in) {
		
		in = in.toLowerCase().replaceAll("[\\.']", "");
		
		String[] array = in.split("\t");
		if (array.length == 3) {
			set.add(array[0]);
		}
		else 
		if (array.length >= 4)
		{
			String cw = String.format("%s %s", array[0], array[1]);
			set.add(cw);
		}
	}
	
	private static void writeFile () {
		final String outDir = String.format("%s\\%s\\%s", usrDir, "corpus",outFile);
		try {
			PrintWriter writer = new PrintWriter(outDir);
			for (String keyword : set) {
				writer.write(keyword + "\n");
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.err.println("Writing File " + outDir);
		System.err.println("Finised! " + set.size());
	}
}
