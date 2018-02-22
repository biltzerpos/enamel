package authoringApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Reads a text file into a string.
 * @author Xiahan Chen, Huy Hoang Minh Cu, Qasim Mahir
 *
 */
public class FileParser {
	
	private LinkedList<String> parsedText;
	
	/**
	 * Initializes this object.
	 * @param file The file to parse.
	 * @throws IOException
	 */
	public FileParser(File file) throws IOException{
		parseFileIntoArray(file);
	}
 
	/**
	 * Parses the file into a LinkedList.
	 * @param file The file to parse.
	 * @throws IOException
	 */
	private void parseFileIntoArray(File file) throws IOException {
		
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String string= "";
        LinkedList<String> lines = new LinkedList<String>();
        while((string = reader.readLine()) != null){
            lines.add(string);
        }
        parsedText = lines;
		reader.close();
	}
	
	/**
	 * 
	 * @return The LinkedList with each element representing a line from the file.
	 */
	public LinkedList<String> getArray() {
		return parsedText;
	}
}
