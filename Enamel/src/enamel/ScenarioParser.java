package enamel;

import java.io.*;
import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.logging.*;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ScenarioParser {
	private Voice voice;
	private VoiceManager vm;
	private Scanner fileScanner;
	private int cellNum;
	private int buttonNum;
	public Player player;
	private boolean repeat;
	private ArrayList<String> repeatedText;
	public boolean userInput;
	private String scenarioFilePath;
	private boolean isVisual;

	public ScenarioParser(boolean isVisual) {

		//String currDir = System.getProperty("user.dir");
        //System.setProperty("mbrola.base", currDir + File.separator + "lib/mbrola.jar");
        vm = VoiceManager.getInstance();
        voice = vm.getVoice ("kevin16");
        voice.allocate();
        repeatedText = new ArrayList<String> ();
        userInput = false;
        this.isVisual = isVisual;
	}
	
	/*
	 * This method exits the program.
	 */
	private void exit() {
		Thread.currentThread().interrupt();
	}

	/*
	 * This method corresponds to the /~reset-buttons key phrase in the
	 * scenario, and it calls the removeListener method on all the JButtons that
	 * were created, to remove their action listeners.
	 */
	private void resetButtons() {
		for (int i = 0; i < player.buttonNumber; i++) {
			player.removeButtonListener(i);
		}
	}

	/*
	 * This method corresponds to the /~skip: key phrase in the scenario, and it
	 * skips to the next occurrence of the identifier specified as the argument.
	 */
	void skip(String indicator) {
		while (fileScanner.hasNextLine()) {
			if (fileScanner.nextLine().equals("/~" + indicator)) {
				break;
			}
		}
		// Logical check to see that there is another occurence of indicator
		// that exists in the scenario file.
		if (!fileScanner.hasNextLine()) {
			fileScanner.close();
			errorLog("Exception error: IllegalArgumentException",
					"Expected the keyphrase: \n" + "/~" + indicator
							+ " \n ,somewhere in the scenario file, to indicate where "
							+ " to skip to, but the keyphrase was nowhere to be found."
							+ "\n The program ended due to an incorrect formatted scenario file.");
		}
	}

	/*
	 * This method interprets each line of the scenario, and performs the
	 * corresponding action depending on the line being read in (i.e reading out
	 * the text in TTS, or changing the display of the Braille cell)
	 */
	void performAction(String fileLine) throws InterruptedException {
		// This statement checks if the key phrase /~repeat has been read. If it
		// has, then everything
		// after the key phrase is assumed to be text to be repeated.
		if (repeat) {
			// Stops assuming that the text is being repeated with the
			// /~endrepeat key phrase
			if (fileLine.length() >= 11 && fileLine.substring(0, 11).equals("/~endrepeat")) {
				repeat = false;
			} else {
				repeatedText.add(fileLine);
				speak(fileLine);
			}
		} else {
			// The key phrase to indicate to play a sound file.
			if (fileLine.length() >= 8 && fileLine.substring(0, 8).equals("/~sound:")) {
				playSound(fileLine.substring(8).trim());
			}
			// The key phrase to indicate to skip to another part of the
			// scenario.
			else if (fileLine.length() >= 7 && fileLine.substring(0, 7).equals("/~skip:")) {
				skip(fileLine.substring(7));
			}
			// The key phrase to indicate to pause for a specified number of
			// seconds.
			else if (fileLine.length() >= 8 && fileLine.substring(0, 8).equals("/~pause:")) {
				pause(fileLine.substring(8));
			}
			// The key phrase to assign a button to repeat text.
			else if (fileLine.length() >= 16 && fileLine.substring(0, 16).equals("/~repeat-button:")) {
				repeatButton(fileLine.substring(16));
			}
			// The key phrase to signal that everything after that key phrase
			// will be repeated.
			else if (fileLine.length() >= 8 && fileLine.substring(0, 8).equals("/~repeat")) {
				repeatedText.clear();
				repeat = true;
			}
			// The key phrase to reset the action listeners of all of the
			// JButtons.
			else if (fileLine.length() >= 15 && fileLine.substring(0, 15).equals("/~reset-buttons")) {
				resetButtons();
			}
			// The key phrase to assign a button to skip to another part of the
			// scenario.
			else if (fileLine.length() >= 14 && fileLine.substring(0, 14).equals("/~skip-button:")) {
				skipButton(fileLine.substring(14));
			}
			// The key phrase to clear the display of all of the braille cells.
			else if (fileLine.length() >= 15 && fileLine.substring(0, 15).equals("/~disp-clearAll")) {
				player.clearAllCells();
			}
			// The key phrase to set a Braille cell to a string.
			else if (fileLine.length() >= 17 && fileLine.substring(0, 17).equals("/~disp-cell-pins:")) {
				dispCellPins(fileLine.substring(17));
			}
			// The key phrase to represent a string in Braille.
			else if (fileLine.length() >= 14 && fileLine.substring(0, 14).equals("/~disp-string:")) {
				player.displayString(fileLine.substring(14));
			}
			// The key phrase to change the cell to represent a character in
			// Braille.
			else if (fileLine.length() >= 17 && fileLine.substring(0, 17).equals("/~disp-cell-char:")) {
				dispCellChar(fileLine.substring(17));
			}
			// The key phrase to raise a pin of the specified Braille cell.
			else if (fileLine.length() >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-raise:")) {
				dispCellRaise(fileLine.substring(18));
			}
			// The key phrase to lower a pin of the specified Braille cell.
			else if (fileLine.length() >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-lower:")) {
				dispCellLower(fileLine.substring(18));
			}
			// The key phrase to clear a Braille cell.
			else if (fileLine.length() >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-clear:")) {
				dispCellClear(fileLine.substring(18));
			} else if (fileLine.length() >= 21 && fileLine.substring(0, 21).equals("/~disp-cell-lowerPins")) {
				dispCellRaise("0");
			}
			// The key phrase to wait for the program to receive a user's input.
			else if (fileLine.length() >= 12 && fileLine.substring(0, 12).equals("/~user-input")) {
				userInput = true;
			}
			// Anything other than the specified commands above, is to be
			// interpreted as text that
			// will be spoken for the user to hear.
			else {
				speak(fileLine);
			}
		}
	}

	/*
	 * This method corresponds to the /~pause key phrase in the scenario, and it
	 * pauses the program for however many seconds specified.
	 */
	private void pause(String paramArgs) {
		try {
			Thread.sleep((long) (Double.parseDouble(paramArgs) * 1000));
		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected format: \n num1 \n where num1"
							+ " is the number of seconds the program waits before continuing. \n" + "Received input: "
							+ paramArgs);
		}
	}

	/*
	 * This method corresponds to the /~repeat-button: key phrase in the
	 * scenario, and it sets the button to repeat the text that was stored in
	 * the variable repeatedText.
	 */
	private void repeatButton(String paramArgs) {
		try {
			int paramIndex = Integer.parseInt(paramArgs);
			if (paramIndex < 0 || paramIndex > player.buttonNumber - 1) {
				errorLog("Exception error: IllegalArgumentException", "Expected button number to be the range of 0 .. "
						+ (player.buttonNumber - 1) + "\n Received input : " + paramArgs);
			}

			player.addRepeatButtonListener(paramIndex, this);

		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected format: \n num1 \n where num1 is"
							+ " the button number to receive the action listener and str1 is the identifier for"
							+ " where to skip to in the scenario file. \n Received input: " + paramArgs);
		}
	}

	/*
	 * This method corresponds to the /~skip-button: key phrase in the scenario,
	 * and it sets the button to skip to the identifier in the second part of
	 * the argument when pressed.
	 */
	private void skipButton(String paramArgs) {
		try {
			String[] param = paramArgs.split("\\s");
			int paramIndex = Integer.parseInt(param[0]);
			if (paramIndex < 0 || paramIndex > player.buttonNumber - 1 || param.length > 2) {
				errorLog("Exception error: IllegalArgumentException",
						"Expected button number to be the range of 0 .. " + (player.buttonNumber - 1)
								+ "\n Or the parameters to have two values. " + "\n Received input: " + paramArgs);
			}

			player.addSkipButtonListener(paramIndex, param[1], this);

		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected format: \n num1 string1 \n where num1 is"
							+ " the button number to receive the action listener and str1 is the identifier for"
							+ " where to skip to in the scenario file. \n Received input: " + paramArgs);
		}
	}

	/*
	 * This method corresponds to the /~disp-cell-pins: key phrase in the
	 * scenario, and it implements the setPins method in the BrailleCell class.
	 */
	private void dispCellPins(String paramArgs) {
		try {
			
			String[] param = paramArgs.split("\\s");
			int paramIndex = Integer.parseInt(param[0]);
			if (param.length > 2 || paramIndex < 0 || paramIndex > player.brailleCellNumber - 1
					|| param[1].length() != 8) {
				errorLog("Exception error: IllegalArgumentException",
						"Expected cell number to be the range of 0 .. " + (player.brailleCellNumber - 1)
								+ "\n Or the string to be an 8 character sequence of "
								+ "1's and 0's. \n Received input : " + paramArgs);
			}
			// For loop used to check if sequence does not have all 1's and 0's.
			for (int i = 0; i <= 7; i++) {
				if (param[1].charAt(i) != '0' && param[1].charAt(i) != '1') {
					errorLog("Exception error: IllegalArgumentException",
							"Expected cell number to be the range of 0 .. " + (player.brailleCellNumber - 1)
									+ "\n Or the string to be an 8 character sequence of "
									+ "1's and 0's. \n Received input : " + paramArgs);
				}
			}
			player.getCell(paramIndex).setPins(param[1]);
			player.refresh();
		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected format: \n num1 string1 \n where num1 is the cell affected, "
							+ "and string1 is the representation of the raised and lowered pins. Also that num1 is a valid"
							+ " interger and string1 is a valid 8 character sequence of 1's and 0's"
							+ ". Received format was: " + paramArgs);
		}
	}

	/*
	 * This method corresponds to the /~disp-cell-char: key phrase in the
	 * scenario, and it implements the displayCharacter method in the
	 * BrailleCell class.
	 */
	private void dispCellChar(String paramArgs) {
		try {
			String[] param = paramArgs.split("\\s");
			int paramIndex = Integer.parseInt(param[0]);
			char dispChar = param[1].charAt(0);
			if (paramIndex > player.brailleCellNumber - 1 || paramIndex < 0 || param[1].length() > 1) {
				errorLog("Exception error: IllegalArgumentException",
						"Expected cell number to be the range of 0 .. " + (player.brailleCellNumber - 1)
								+ "\n Or the character to be any letter of the English"
								+ "alphabet, either lower or upper case. \n Received input : " + paramArgs);
			}
			player.getCell(paramIndex).displayCharacter(dispChar);
			player.refresh();

		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected format: \n num1 char1 \n where num1 is the cell affected, "
							+ "and char1 is the character to be displayed. Also that num1 is a valid interger and char1 is a valid "
							+ "English alphabet letter. Received format was: " + paramArgs);
		}
	}

	/*
	 * This method corresponds to the /~disp-cell-raise: key phrase in the
	 * scenario, and it implements the raiseOnePin method in the BrailleCell
	 * class.
	 */
	private void dispCellRaise(String paramArgs) {
		try {
			String[] param = paramArgs.split("\\s");
			int paramIndex = Integer.parseInt(param[0]);
			int pinNum = Integer.parseInt(param[1]);
			if (paramIndex > player.brailleCellNumber - 1 || paramIndex < 0 || param.length > 7) {
				errorLog("Exception error: IllegalArgumentException",
						"Expected cell number to be the range of 0 .. " + (player.brailleCellNumber - 1)
								+ "\n Or pin number to be the range of 1 .. 8 \n Received input : " + paramArgs);
			}
			player.getCell(paramIndex).raiseOnePin(pinNum);
			player.refresh();
		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected format: \n num1 num2 \n where num1 is the cell affected, "
							+ "and num2 is the pin affected. Also that num1 and num2 are valid intergers. "
							+ "Received format was: " + paramArgs);
		}
	}

	/*
	 * This method corresponds to the /~disp-cell-clear: key phrase in the
	 * scenario, and it implements the clear method in the Braille class.
	 */
	private void dispCellClear(String paramArgs) {
		try {
			int cellIndex = Integer.parseInt(paramArgs);
			if (cellIndex < 0 || cellIndex > player.brailleCellNumber - 1) {
				errorLog("Exception error: IllegalArgumentException", "Expected cell number to be in the range"
						+ " of 0 .. " + (player.brailleCellNumber - 1) + "\n Received cell number was: " + cellIndex);
			}
			player.getCell(cellIndex).clear();
			player.refresh();
		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(), "Expected format: num1 \n where num1 is the cell affected, "
					+ "and that num1 is a valid integer. Received format was: " + paramArgs);
		}
	}

	/*
	 * This method corresponds to the /~disp-cell-lower: key phrase in the
	 * scenario file, and it implements lowerOnePin method in the Braille class.
	 */
	private void dispCellLower(String paramArgs) {
		try {
			String[] param = paramArgs.split("\\s");
			int paramIndex = Integer.parseInt(param[0]);
			int pinNum = Integer.parseInt(param[1]);
			if (paramIndex > player.brailleCellNumber - 1 || paramIndex < 0 || pinNum < 1 || pinNum > 8
					|| param.length > 2) {
				errorLog("Exception error: IllegalArgumentException",
						"Expected cell number to be the range of 0 .. " + (player.brailleCellNumber - 1)
								+ "\n Or pin number to be the range of 1 .. 8 \n Received input : " + paramArgs);
			}
			player.getCell(paramIndex).lowerOnePin(pinNum);
			player.refresh();

		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected format: \n num1 num2 \n where num1 is the cell affected, "
							+ "and num2 is the pin affected. Also that num1 and num2 are valid intergers. "
							+ "Received format was: " + paramArgs);
		}
	}

	/*
	 * This method speaks out loud using the TTS methods created by FreeTTS.
	 */
	private void speak(String text) {
		try {
			voice.speak(text);
		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Possible error could be that you are missing some specific jar files"
							+ "from the FreeTTS website or that you may have removed them by accident.");
		}
	}

	/*
	 * This method reads all the lines of the scenario file.
	 */
	private void play() {
		String fileLine;
		try {
			while (fileScanner.hasNextLine()) {
				// This while loop is created to wait for a user to press a
				// button.
				while (userInput) {
					Thread.sleep(400);
				}
				fileLine = fileScanner.nextLine();
				performAction(fileLine);
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

	/*
	 * This method logs any logical errors or exception errors that may occur at
	 * any time during the program.
	 */
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

	/*
	 * This method corresponds to the /~sound: key phrase in the scenario file,
	 * and it plays the sound files where the argument is the name of the sound
	 * file.
	 */
	private void playSound(String sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(
					new File(scenarioFilePath + File.separator + "AudioFiles" + File.separator + sound)));
			clip.start();
			// This while loop is to check if the audio file has played or not,
			// and if it has not then it will
			// continue to wait until it does.
			while (!clip.isRunning())
				Thread.sleep(10);
			while (clip.isRunning())
				Thread.sleep(10);
			clip.close();

		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected the name of the file (including extension) but instead got: " + sound
							+ "\n Perhaps you forgot to include the extension of the sound file with the name? Other "
							+ "possibilities include: \n Incorrect name of the file, the file not being in the same location "
							+ "as the project folder, or an attempt to play an unsupported sound file. (only .wav files"
							+ "are supported at this time)");
		}
	}

	/*
	 * This method repeats the text of the lines stored in between /~repeat and
	 * /~endrepeat.
	 */
	void repeatText() {
		for (String i : repeatedText) {
			speak(i);
		}
	}

	/*
	 * This method initializes the simulator class, by interpreting the first
	 * two lines of the scenario file to indicate the number of cells and number
	 * of JButtons, respectively.
	 */
	private void setCellAndButton() {
		try {
			cellNum = Integer.parseInt(fileScanner.nextLine().split("\\s")[1]);
			buttonNum = Integer.parseInt(fileScanner.nextLine().split("\\s")[1]);
			if (isVisual)
			    player = new VisualPlayer(cellNum, buttonNum);
			else
			    player =  new AudioPlayer(cellNum, buttonNum);
		} catch (Exception e) {

			errorLog("Exception error: " + e.toString(),
					"Expected format: Cell num1 \n Button num2 \n "
							+ "as the first two lines of the scenarion file, and where num1 and num2 are positive integers. \n"
							+ "Did not receive such a format in the scenario file and program had to end due to the incorrect"
							+ "file format.");
		}
	}

	/*
	 * This method plays the scenario file specified by the argument. The
	 * argument can be specified either as an absolute or a relative path.
	 */
	public void setScenarioFile(String scenarioFile) {
		try {

			File f = new File(scenarioFile);
			fileScanner = new Scanner(f);
			String absolutePath = f.getAbsolutePath();
			scenarioFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
			setCellAndButton();
			play();
		} catch (Exception e) {
			errorLog("Exception error: " + e.toString(),
					"Expected the directory path of the scenario file to"
							+ " a file exists in the project folder. \n Could not find directory to path: "
							+ scenarioFile + " \n Perhaps" + " you forgot to add the file to the directory or "
							+ " you are looking for a different directory?");
		}
	}

}
