package dictionary.driver;

import org.hibernate.Session;

import java.util.List;

import dictionary.model.DictionaryEntry;
import dictionary.model.FileUtils;
import dictionary.utils.DatabaseUtil;
import java.util.ArrayList;

public class FileUtilsTestDriver {
	public static void main(String[] args) {
		FileUtils utils = new FileUtils();
		
		// valid file
		if (!dictionary.model.FileUtils.checkFileType("validfile.csv"))
			throw new AssertionError();
		
		// invalid file (bad extension)
		if (dictionary.model.FileUtils.checkFileType("invalidfile.txt"))
			throw new AssertionError();
		
		// invalid file	(short)
		if (dictionary.model.FileUtils.checkFileType("l"))
			throw new AssertionError();
		
		
		ArrayList<String> lines = utils.loadFile("test_file.csv");
		
		// there should be three lines
		if (lines.size() != 3)
			throw new AssertionError();
		
		List<DictionaryEntry> entries = utils.getEntries("entry_list.csv");
		for (DictionaryEntry entry: entries) {
			System.out.print(entry.getWordRoot().getWordForm() + "\n");
		}
	}
}
