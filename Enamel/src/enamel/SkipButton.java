package enamel;

import java.io.*;
import java.util.*;

public class SkipButton extends NodeButton {
	protected String response;
	protected Node nextNode;
	protected int[] pins = new int[8];
	protected String audioFile;
	
	public SkipButton(int buttonNumber) {
		super(buttonNumber);
		this.response = "";
		this.nextNode = null;
		this.audioFile = "";
		// TODO Auto-generated constructor stub
	}

	public SkipButton(int buttonNumber, String response) {
		super(buttonNumber);
		this.response = response;
		this.nextNode = null;
		this.audioFile = "";
		// TODO Auto-generated constructor stub
	}

	public SkipButton(int buttonNumber, String response, Node nextNode) {
		super(buttonNumber);
		this.response = response;
		this.nextNode = nextNode;
		this.audioFile = "";

		// TODO Auto-generated constructor stub
	}
	
	public SkipButton(int buttonNumber, String response, String audioFile, Node nextNode) {
		super(buttonNumber);
		this.response = response;
		this.nextNode = nextNode;
		this.audioFile = audioFile;

		// TODO Auto-generated constructor stub
	}

	public SkipButton(int buttonNumber, String response, Node nextNode, int[] pins) {
		super(buttonNumber);
		this.response = response;
		this.nextNode = nextNode;
		this.setPins(pins);
		this.audioFile = "";

		// TODO Auto-generated constructor stub
	}
	
	public SkipButton(int buttonNumber, String response, Node nextNode, int[] pins, String audioFile) {
		super(buttonNumber);
		this.response = response;
		this.nextNode = nextNode;
		this.setPins(pins);
		this.audioFile = audioFile;

		// TODO Auto-generated constructor stub
	}

	public SkipButton(int buttonNumber, Node nextNode) {
		super(buttonNumber);
		this.response = "";
		this.nextNode = nextNode;
		this.audioFile = "";

		// TODO Auto-generated constructor stub
	}

	public SkipButton(SkipButton other) {
		super(other);
		this.response = other.response;
		this.nextNode = other.nextNode;
		this.audioFile = other.audioFile;

		// TODO Auto-generated constructor stub
	}
	
	public String getResponse() {
		return this.response;
	}
			
	public void setResponse(String response) {
		this.response = response;
	}
	
	public void setNextNode(Node next) {
		this.nextNode = next;
	}
	
	public Node getNextNode() {
		return this.nextNode;
	}

	/**
	 * @return the listOfPins
	 */
	public int[] getPins() {
		return pins;
	}

	/**
	 * @param pins the listOfPins to set
	 */
	public void setPins(int[] pins) {
		this.pins = pins;
	}
	
	public void setAudioFile(String audioFile) {
		try {
			Scanner file = new Scanner(new File(audioFile));
			file.close();
			this.audioFile = audioFile;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException("The file name does not exist"); 
		}
		
		
	}
	
	public String getAudioFile() {
		return this.audioFile;
	}

}

