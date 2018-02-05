package authoringApp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class FileParser {
	
	private ArrayList<String> parsedText;
	
	
	// Constructor
	public FileParser(File file) throws IOException{
		
		// TODO Auto-generated constructor stub
		parseFileIntoArray(file);
	}

	// Parse the file and save it into the array parsedText
	private void parseFileIntoArray(File file) throws IOException {
		
		Scanner scanner = new Scanner(file);
		
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String string= "";
        ArrayList<String> lines = new ArrayList<String>();
        while((string = reader.readLine()) != null){
            lines.add(string);
        }
       parsedText=lines;
		
	}
	
	// Returns the parsed file;
	public ArrayList<String> getArray() {
		return parsedText;
	}

	public LinkedList<String> getLinkedList() {
		
		
		return null;
	}
}
