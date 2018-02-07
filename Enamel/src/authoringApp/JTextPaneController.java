package authoringApp;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JTextPane;
import javax.xml.soap.Node;

public class JTextPaneController extends JTextPane{
	
	JTextPane editor;
	LinkedList<String> scenarioList; //List of strings to be used in text pane
	int lineCount;
	String[] scenarioArray;
	private Node head;
	
	
	
	
	//Constructor
	JTextPaneController(JTextPane o, File f) throws IOException {
		
		this.editor = o;
		FileParser fp = new FileParser(f);
		scenarioList = fp.getLinkedList();
		
		updateTextPane();
	}
	
	public int getLines() {
		return scenarioList.size();
	}
	
	public LinkedList<String> getList() {
		return scenarioList;
	}
	
	public void addToLinkedList(String s) {
		//TODO: adds a string to the end of the scenarioList.
		
		scenarioList.addLast(s);
		
	}

	public void addToLinkedList(String s, int n) {
		//TODO: adds a string to the scenarioList where n is the index to
		//insert the string.
		scenarioList.add(n-1, s);
	}
	
	public LinkedList<String> parseTextPane() {
		//TODO: parses the text pane and store each line into nodes of the linked list
		//Each line in the text pane should be of format "n: the string" where n is
		//the line number. The parsed string should not contain "n:"
		
		
		return null;
	}
	
	public void updateTextPane() {
		//TODO: prints the scenarioList into the TextPane line by line.
		//Each line should have the "n:" prefix where n is the line number.
		for(int i =0; i<scenarioList.size();i++) {
			editor.setText(String.valueOf(scenarioList));   //not sure abt this one
			
		}
	}
}
