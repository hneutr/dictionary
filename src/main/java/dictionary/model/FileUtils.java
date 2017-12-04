package dictionary.model;

import java.util.List;
import java.nio.file.*;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author alexkillian
 *
 * File utilities needed to make file management easier.
 */
public class FileUtils {
	
	private String baseDir = "";
	
	/**
	 * Checks the type of the given file.
	 * 
	 * @param file
	 * @return true if the type is valid, false otherwise.
	 */
	public static boolean checkFileType(String file) {
		
		
		if ( file.length() > 3 ) {
			String extension = file.substring(file.length() - 3);
			
			if (extension.equals("csv"))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Loads the given file.
	 * 
	 * @return
	 */
	public ArrayList<String> loadFile(String file) {
		Path path = Paths.get(baseDir + file);
		
		Stream<String> lineStream = null;
		
		ArrayList<String> realList = new ArrayList<String>();
		
		try {
			lineStream = Files.lines(path);
			
			for (Object element : lineStream.collect(Collectors.toList())) {
				String string = element.toString();
				realList.add(string);
			}
		}
		catch (IOException e) {
		    System.err.println("IOException: " + e.getMessage());	
		} finally {
			lineStream.close();
		}
		
		return realList;
	}
	
	/**
	 * Parses the entries from the file contents.
	 * 
	 * @param content
	 * @return
	 */
	private ArrayList<DictionaryEntry> parseEntries(String content) {
		CSVParser parser;
		ArrayList<DictionaryEntry> entryList = new ArrayList<DictionaryEntry>();
		
		try {
			parser = CSVParser.parse(content, CSVFormat.EXCEL);
			
			for (CSVRecord record: parser.getRecords()) {
				WordForm root = new WordForm(record.get("wordRoot"));
				DictionaryEntry entry = new DictionaryEntry(root);
				
				String defString = record.get("definition");
				if (defString != null) {
					Definition definition = new Definition(defString);
					WordSense ws = new WordSense(definition, null);
					entry.addSense(ws);
				}
				
				entryList.add(entry);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entryList;
	}
	
	/**
	 * Loads a file and returns the entries from it.
	 * 
	 * @param file
	 * @return
	 */
	public List<DictionaryEntry> getEntries(String file) {
		if (checkFileType(file)) {
			ArrayList<String> lines = loadFile(file);
			for (String line: lines) {
				ArrayList<DictionaryEntry> entries = parseEntries(line);
			}
		}
		return null;
	}
	
}
