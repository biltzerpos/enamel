package enamel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sun.speech.freetts.Voice;

public class ScenarioNode {
	
	private Scanner fileScanner;
	private String scenarioFilePath;
	private Voice voice;
	private boolean repeat;
	//tracks how many nodes were created (may need to be static?):
	private int nodeTrack;
	BrailleCell b = new BrailleCell();
	Scenario p = new Scenario();
	private int numOfNodes;
	private int numOfCells;
	private int[] numberOfButtons;
	private int numOfButtons; //number of buttons passed by scenario file
	
	/*
	 * What does this class do?
	 * It will take in each "node" of the scenario.
	 * Within the provided scenarios, each scenario node is delimited by a
	 * certain phrase.
	 */
	
	public int nodeTrack() {
		return this.nodeTrack;
	}
	
	private void nodeTrackIncrement() {
		this.nodeTrack++;
	}
	
	private void nodeDelimiter(String fileLine) {
		String nodeName;
		if (fileLine.length() >= 4 && fileLine.substring(0, 5).equals("Cells") && 
			fileLine.substring(6).matches("^[0-9]*[1-9][0-9]*$")) {
			int num = Integer.parseInt(fileLine.substring(6));
			this.numOfCells = num;
		}
		if (fileLine.length() >= 4 && fileLine.substring(0, 6).equals("Button") && 
				fileLine.substring(7).matches("^[0-9]*[1-9][0-9]*$")) {
			int num = Integer.parseInt(fileLine.substring(7));
			this.numOfButtons = num;
		}
		if (this.nodeTrack == 0){ //starting with first node
			Node firstNode = p.createNode();
			if (fileLine.length() >= 8 && fileLine.substring(0, 8).equals("/~sound:")) {
				String soundFile = fileLine.substring(8);
				//this.sound = fileLine.substring(8);
			}
			else if (fileLine.length() >= 7 && fileLine.substring(0, 7).equals("/~skip:")) {
				String skipLine = fileLine.substring(7);
				//need to connect each response node to the next node (NEXTT)
			}
			else if (fileLine.length() >= 8 && fileLine.substring(0, 8).equals("/~pause:")) {
				//where to store pause element? ***********
				//this.pause = fileLine.substring(8);
			}
			else if (fileLine.length() >= 16 && fileLine.substring(0, 16).equals("/~repeat-button:")) {
				String repeatButtonLine = fileLine.substring(16);
				int buttonIndex = Integer.parseInt(repeatButtonLine);
				//buttonIndex may not be a unique integer because skip button also produces integers
				//to account for this, we add 100
				firstNode.addButton(buttonIndex + 100);
				numberOfButtons[nodeTrack]++;
			}
			else if (fileLine.length() >= 8 && fileLine.substring(0, 8).equals("/~repeat")) {
				this.repeat = true;
				if (!firstNode.getRepeatedText().isEmpty()){
					firstNode.setRepeatedText(null);
				}
			}
			else if (this.repeat) {
				if (fileLine.length() >= 11 && fileLine.substring(0, 11).equals("/~endrepeat")) {
					this.repeat = false;
				} else {
					firstNode.addToRepeatedText(fileLine);
				}
			}
			else if (fileLine.length() >= 15 && fileLine.substring(0, 15).equals("/~reset-buttons")) {
				//sets all buttons to be unresponsive
			}
			else if (fileLine.length() >= 14 && fileLine.substring(0, 14).equals("/~skip-button:")) {
				String skipLine = fileLine.substring(14); //gives string after "/~skip-button:"
				String[] split = skipLine.split("\\s"); //split string delimited by space
				int buttonIndex = Integer.parseInt(split[0]); //jbutton index
				String button = split[1]; //key phrase that button will skip to
				firstNode.addButton(buttonIndex);
				Node next = p.createNode(button);
				p.setEdge(firstNode, next, buttonIndex);
				numberOfButtons[nodeTrack]++;
			}
			else if (fileLine.length() >= 15 && fileLine.substring(0, 15).equals("/~disp-clearAll")) {
				int[] pins = new int[8];
				for (int i = 0; i < this.numOfCells; i++) {
					firstNode.setPins(pins, i);
				}
			}
			else if (fileLine.length() >= 17 && fileLine.substring(0, 17).equals("/~disp-cell-pins:")) {
				String breakDown = fileLine.substring(17);
				String[] split = breakDown.split("\\s");
				int brailleCell = Integer.parseInt(split[0]);
				for (int i = 0; i < 8; i++) {
					firstNode.setPin(brailleCell, i, split[1].charAt(i));	
				}
			}
			else if (fileLine.length() >= 14 && fileLine.substring(0, 14).equals("/~disp-string:")) {
				String line = fileLine.substring(14);
				/*
				 * Functionality note:  if string is less than cells displayed, will leave remaining cells empty.
				 * If string is greater than cells displayed, then it will be cut off.
				 */
				int i = 0;
				while (i < line.length() && line.length() <= this.numOfCells) {
					char a = line.charAt(i);
					String brailleString = b.getCharacter(a);
					for (int n = 0; i < 8; n++) {
						int k = Character.getNumericValue(brailleString.charAt(n));
						firstNode.setPin(i, n, k);
					}
				}
				//display string in braille cells, if greater than number of cells, then string will be cut off
				//if smaller than number of cells, remaining cells are cleared
			}
			else if (fileLine.length() >= 17 && fileLine.substring(0, 17).equals("/~disp-cell-char:")) {
				/*
				 * Limitation: this does not read capital letters, only lowercase (limitation of braille alphabet
				 * in BrailleCell class).  Will need to add to alphabet for uppercase.
				 */
				String charac = fileLine.substring(17);
				String[] param = charac.split("\\s");
				int brailleCell = Integer.parseInt(param[0]);
				char dispChar = param[1].charAt(0);
				String characterBraille = b.getCharacter(dispChar);
				for (int i = 0; i < 8; i++) {
					int k = Character.getNumericValue(characterBraille.charAt(i));
					firstNode.setPin(brailleCell, i, k);
				}
			}
			else if (fileLine.length() >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-raise:")) {
				String breakdown = fileLine.substring(18);
				String[] param = breakdown.split("\\s");
				int brailleCell = Integer.parseInt(param[0]);
				int pinToRaise = Integer.parseInt(param[1]);
				firstNode.setPin(brailleCell, pinToRaise, 1);
			}
			else if (fileLine.length() >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-lower:")) {
				String breakdown = fileLine.substring(18);
				String[] param = breakdown.split("\\s");
				int brailleCell = Integer.parseInt(param[0]);
				int pinToRaise = Integer.parseInt(param[1]);
				firstNode.setPin(brailleCell, pinToRaise, 0);
			}
			else if (fileLine.length() >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-clear:")) {
				String breakdown = fileLine.substring(18);
				String[] param = breakdown.split("\\s");
				int brailleCell = Integer.parseInt(param[0]);
				int[] pins = new int[8];
				firstNode.setPins(pins, brailleCell);
			}
			/* this key phrase is not on the scenario format document
			else if (fileLine.length() >= 21 && fileLine.substring(0, 21).equals("/~disp-cell-lowerPins")) {
				dispCellRaise("0");
			}
			*/
			else if (fileLine.length() >= 12 && fileLine.substring(0, 12).equals("/~user-input")) {
				//nothing needs to be done here as the nodes and edges are created with /~skip-button
				nodeTrackIncrement();
			}
			else { //no key phrase, therefore must be plain text
				firstNode.setResponse(fileLine);
			}
		}
		if (this.nodeTrack > 0){ // i.e., not the first node
			//will fill in with the same control statements as above
		}
	}
	
	private void play() {
		String fileLine;
		try {
			while (fileScanner.hasNextLine()) {
				fileLine = fileScanner.nextLine();
				nodeDelimiter(fileLine);
			}
			if (!fileScanner.hasNextLine()) {
				fileScanner.close();
				// The if statement is created to check if there is an
				// /~endrepeat for a previously
				// declared /~repeat in the scenario file.
				if (repeat) {
					errorLog("Exception error: IllegalArgumentException",
							"Expected the keyphrase: \n" + "/~endrepeat "
									+ "\n ,somewhere in the scenario file, to indicate when "
									+ " to stop storing the text to be repeated, but the keyphrase was "
									+ "nowhere to be found." + "\n The program ended due to an "
									+ "incorrect formatted scenario file.");
				}
				exit();
			}
		} catch (Exception e) {
			errorLog("Exception error : " + e.toString(),
					"Strange error occurred if you are able to read this message. Possibilities "
							+ "could include possible file corruption, or that you have enter characters that "
							+ "could not be read/interpreted.");
		}
	}
	
	public void setScenarioFile(String scenarioFile) {
		try {

			File f = new File(scenarioFile);
			fileScanner = new Scanner(f);
			String absolutePath = f.getAbsolutePath();
			scenarioFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
			play();
		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected the directory path of the scenario file to"
							+ " a file exists in the project folder. \n Could not find directory to path: "
							+ scenarioFile + " \n Perhaps" + " you forgot to add the file to the directory or "
							+ " you are looking for a different directory?");
		}
	}
	
	private void errorLog(String exception, String message) {
		Logger logger = Logger.getLogger("ERROR_LOG");
		FileHandler fh;

		System.out.println(message);

		speak("Error! Something went wrong in the program! Please consult a teacher "
				+ "or administrator for assistance! Also please view the ERROR_LOG file for more details");
		// The try-catch block is to format the Logger class so that the error
		// log file is easier to understand.
		try {
			File f = new File("ERROR_LOG.txt");
			fh = new FileHandler(f.toString());

			logger.addHandler(fh);
			logger.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			logger.warning(exception);
			logger.info(message);
			fh.close();

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exit();
	}
	
	private void speak(String text) {
		try {
			voice.speak(text);
		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Possible error could be that you are missing some specific jar files"
							+ "from the FreeTTS website or that you may have removed them by accident.");
		}
	}
	
	private void exit() {
		Thread.currentThread().interrupt();
	}

}
