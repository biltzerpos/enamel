package enamel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
* This class provides abstract and concrete methods for simulating a braille cell.
* <p>
* The constructor initializes the window and takes as arguments the number of
* Braille Cells and the number of buttons the simulator should display. This
* class also contains several methods that allow for the manipulation of the
* Player, some implemented and others remain abstract for future implementation.
* 
* @author ENAMEL team: Sunjik Lee, Li Yin, Vassilios Tzerpos.
*
*/
public abstract class Player {

	int brailleCellNumber;
	int buttonNumber;
	LinkedList<BrailleCell> brailleList = new LinkedList<BrailleCell>();
	Logger logger = Logger.getLogger(this.getClass().getName());
	int repeat = 0;
	
	/**
     * Constructor for the Player abstract class, used by classes that extend
     * this class. The constructor contains the basic implementation necessary
     * for all Player subclasses, such as checking the validity of brailleCellNumber 
     * and buttonNumber (non-negative), as well as creating the specified
     * number of BrailleCell objects and adding it to the <code>brailleList</code> List. 
     * 
     * @param brailleCellNumber
     *            the number of braille cells the Player should have
     * @param buttonNumber
     *            the number of buttons the Player should have
     * @throws IllegalArgumentException
     *             if one or both of the two parameters is negative or 0
     */
	public Player(int brailleCellNumber, int buttonNumber) {

	    //Formatting the Logger for the player class, which all its child classes uses. 
		//Change the formatting as needed. 
		//Currently, it's set to ConsoleHandler instead of FileHandler. It will write the log
		//to the console. 
		//Eventually, we'll need to have it save to a file. Simply change ConsoleHandler to FileHandler,
		//and set the output to the appropriate directory. 
		
		//To find out what's being logged, search and find any "logger.log" calls.
		ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
    		private String format = "[%1$s] [%2$s] %3$s %n";
			private SimpleDateFormat dateWithMillis = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
			@Override
			public String format(LogRecord record) {
				return String.format(format, dateWithMillis.format(new Date()), record.getSourceClassName(), formatMessage(record));
			}
    	});
    	logger.addHandler(consoleHandler);
    	logger.setUseParentHandlers(false);
	    
		if (brailleCellNumber <= 0 || buttonNumber <= 0)
			throw new IllegalArgumentException("Non-positive integer entered.");

		this.brailleCellNumber = brailleCellNumber;
		this.buttonNumber = buttonNumber;

		for (int i = 0; i < brailleCellNumber; i++) {
			BrailleCell cell = new BrailleCell();
			brailleList.add(cell);
		}
	}
	
	public Player() {
	    this(1,1);
	}

	/**
	 * Returns a reference to the BrailleCell object at the index passed as
	 * argument. 
	 * 
	 * @param index
	 *            the index of the BrailleCell object whose reference is to be
	 *            returned
	 * @return reference to the BrailleCell object at the index passed as
	 *         argument
	 * @throws IllegalArgumentException
	 *             if the index is negative or equal to or bigger than
	 *             brailleCellNumber (the number of Braille Cells initialized)
	 */
	public BrailleCell getCell(int index) {
		if (index >= this.brailleCellNumber || index < 0) {
			throw new IllegalArgumentException("Invalid cell index.");
		}
		return this.brailleList.get(index);
	}

	/**
	 * Clears all the Braille Cells, i.e lowers all the pins for all of them,
	 * effectively making them show nothing.
	 */
	public void clearAllCells() {
		for (int i = 0; i < this.brailleCellNumber; i++) {
			this.brailleList.get(i).clear();
		}
	}

	/**
	 * Displays the string passed as argument on all the Braille Cells. If the
	 * string is shorter than the total number of Braille Cells, the remaining
	 * cells are cleared. However, if the string is longer it only displays the
	 * part of it up to however many Braille Cells there are and ignores the
	 * rest.
	 * 
	 * @param aString
	 *            the String to be displayed on the BrailleCells
	 */
	public void displayString(String aString) throws InterruptedException {
		this.clearAllCells();
		for (int i = 0; i < this.brailleCellNumber && i < aString.length(); i++) {
			this.brailleList.get(i).displayCharacter(aString.charAt(i));
		}
		refresh();
	}
	/**
	 * An abstract method to refresh the current implementation's "display" of 
	 * its BrailleCells, whether the display is audio, visual, or hardware, 
	 * or other implementations that extend this class.
	 */
	public abstract void refresh();

	/**
	 * Adds a "Skip Button" for the ScenarioParser's class to continue
	 * processing text after the ScenarioParser's <code>userInput</code> is
	 * set to true. Changes <code>userInput</code> to false to allow the
	 * ScenarioParser to continue.
	 * @param index
	 * 			the index of the button to add the listener to
	 * @param param
	 * 			the String in ScenarioParser to skip to, needed for ScenarioParser's <code>skip(String indicator)</code>
	 * 			method
	 * @param sp
	 * 			the reference to the current ScenarioParser object
	 */
	public abstract void addSkipButtonListener(int index, String param, ScenarioParser sp);

	/**
	 * Removes the Listener on the Button specified by the argument. 
	 * @param index
	 * 			the index of the button
	 */
	public abstract void removeButtonListener(int index);

	/**
	 * Adds a "Repeat Button" for the ScenarioParser's class to repeat
	 * the text.
	 * @param index
	 * 			the index of the button to add the listener to
	 * @param sp
	 * 			the reference to the current ScenarioParser object
	 */
	public abstract void addRepeatButtonListener(int index, ScenarioParser sp);

	public void setup() {
		// TODO Auto-generated method stub
		
	}
}
