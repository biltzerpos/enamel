package authoringApp;

import java.io.File;

public class FileParser {
	
	private String[] parsedText;
	
	// Constructor
	public FileParser(File file) {
		// TODO Auto-generated constructor stub
		parseFileIntoArray(file);
	}

	// Parse the file and save it into the array parsedText
	private void parseFileIntoArray(File file) {
		
	}
	
	// Returns the parsed file;
	public String[] getArray() {
		return parsedText;
	}
}
