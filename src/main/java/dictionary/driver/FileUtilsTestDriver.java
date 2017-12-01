package dictionary.driver;

import org.hibernate.Session;

import dictionary.model.DictionaryEntry;
import dictionary.model.FileUtils;
import dictionary.utils.DatabaseUtil;

public class FileUtilsTestDriver {
	public static void main(String[] args) {
		// valid file
		if (!dictionary.model.FileUtils.checkFileType("validfile.csv"))
			throw new AssertionError();
		
		// invalid file (bad extension)
		if (dictionary.model.FileUtils.checkFileType("invalidfile.txt"))
			throw new AssertionError();
		
		// invalid file	(short)
		if (dictionary.model.FileUtils.checkFileType("l"))
			throw new AssertionError();
	}
}
