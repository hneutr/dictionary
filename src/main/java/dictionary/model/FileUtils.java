package dictionary.model;

import java.util.List;
import java.io.File;

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
	private String loadFile() {
		return "";
	}
	
	/**
	 * Parses the entries from the file contents.
	 * 
	 * @param content
	 * @return
	 */
	private List<DictionaryEntry> parseEntries(String content) {
		return null;
	}
	
	/**
	 * Loads a file and returns the entries from it.
	 * 
	 * @param file
	 * @return
	 */
	public List<DictionaryEntry> getEntries(String file) {
		return null;
	}
	
}
