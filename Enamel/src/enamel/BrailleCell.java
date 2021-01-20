package enamel;

import java.util.HashMap;
import java.util.*;

/**
 * This class implements a Braille Cell with 8 pins. The class contains methods
 * for displaying letters on the cell as well as raising and/or lowering
 * individual pins.
 * 
 * The pins are boolean objects stored in a boolean array field called 
 * <code>listOfPins</code>. boolean allows abstracting the pins, which by 
 * definition can only have two states. This allows for graphical, auditory, and
 * other implementations (i.e. hardware braille cell, Metec B11) to show the 
 * braille cell to the user in different forms. 
 * 
 * <p>
 * This class supports the standard Braille encoding of English characters plus
 * the space character using the <code> displayCharacter()</code> method. For
 * any non-standard characters or ones that aren't included by default, use the
 * <code> setPins()</code> method, which takes a string of 1s and 0s as argument
 * and sets the pins accordingly. 1 meaning raised and 0 meaning lowered.
 * 
 * The constructor of this class is not public. Objects are created when a
 * <code> Simulator</code> object is created. Use the <code> getCell</code>
 * method of class <code> Simulator</code> to obtain references to individual
 * <code> BrailleCell</code> objects.
 * 
 * @author Team 4: Yassin Mohamed, Qassim Allauddin, Derek Li, Artem Solovey.
 * @author ENAMEL team: Sunjik Lee, Li Yin, Vassilios Tzerpos.
 *
 */
public class BrailleCell {

	boolean[] listOfPins = new boolean[8];
	private static HashMap<Character, String> alphabet = new HashMap<Character, String>();

	/**
	 * 
	 * Class constructor. Initializes the <code>alphabet</code> HashMap to populate it
	 * with the appropriate alphabet, which in this case is the English alphabet,
	 * and the corresponding String representations of the braille cell.
	 */
	public BrailleCell() {
		this.initializeAlphabet();
	}

	/**
	 * Initializes the HashMap of our Braille alphabet, with the Key being the char
	 * English alphabet letter and the Value being the corresponding 8 pins represented
	 * as String, with 1 representing a raised pin and 0 representing a lowered pin. 
	 */
	private void initializeAlphabet() {
		alphabet.put('a', "10000000");
		alphabet.put('b', "11000000");
		alphabet.put('c', "10100000");
		alphabet.put('d', "10011000");
		alphabet.put('e', "10001000");
		alphabet.put('f', "11010000");
		alphabet.put('g', "11011000");
		alphabet.put('h', "11001000");
		alphabet.put('i', "01010000");
		alphabet.put('j', "01011000");
		alphabet.put('k', "10100000");
		alphabet.put('l', "11100000");
		alphabet.put('m', "10110000");
		alphabet.put('n', "10111000");
		alphabet.put('o', "10101000");
		alphabet.put('p', "11110000");
		alphabet.put('q', "11111000");
		alphabet.put('r', "11101000");
		alphabet.put('s', "01110000");
		alphabet.put('t', "01111000");
		alphabet.put('u', "10100100");
		alphabet.put('v', "11100100");
		alphabet.put('w', "01011100");
		alphabet.put('x', "10110100");
		alphabet.put('y', "10111100");
		alphabet.put('z', "10101100");
		alphabet.put(' ', "11111111");
	}
	/**
	 * Displays the character passed as argument on this Braille Cell object in
	 * Braille.
	 * <p>
	 * The method works by selecting specific booleans in <code>listOfPins</code> that represent
	 * raised "pins", or the parts of the letter that would be felt by a human
	 * hand, and setting it to true, while setting the "pins" that are not
	 * raised to false. 
	 * 
	 * @param a
	 * 			the char letter to display.
	 * @throws InterruptedException
	 * 			if the argument is not within our alphabet of English letters.
	 */
	public void displayCharacter(char a) throws InterruptedException {

		a = Character.toLowerCase(a);
		if (!alphabet.containsKey(a)) {
			throw new IllegalArgumentException("Non standard character");
		}
		this.setPins(alphabet.get(a));
	}
	
	/**
	 * Takes an eight character string of 1s and 0s as argument, and sets the
	 * the 8 pins accordingly. 1 corresponds to raising the pin, 0 to lowering it.
	 * The first 3 characters correspond to the top-left pin to the
	 * third from the top pin on the left side. The second 3 characters correspond to the top-right 
	 * pin to the third from the top pin on the right side. The last 2 characters correspond
	 * to the bottom-left and the bottom-right pins. This arrangement is to support to the
	 * Metec B11 Braille Cell's arrangement of the pins, which is arranged the same.
	 * 
	 * @param pins
	 *            string of 1s and 0s that is 8 characters long that sets the
	 *            state of the eight pins that form the cell
	 * @throws IllegalArgumentException
	 *             if the string is not 8 characters long or if it contains any
	 *             character that isn't a 1 or a 0
	 */
	
	public void setPins(String pins) {
		// checking for correct index
		if (pins.length() != 8) {
			throw new IllegalArgumentException("Illegal string passed, length > or < 8.");
		}
		for (int i = 0; i <= 7; i++) {
			if (pins.charAt(i) != '0' && pins.charAt(i) != '1') {
				throw new IllegalArgumentException(
						"Invalid string passed, non-binary character detected at index:" + i + ".");
			}
		}
		this.clear();
		for (int i = 0; i < 8; i++) {
			if (pins.charAt(i) == '1') {
				listOfPins[i] = true;
			}
		}
	}
	
	/**
	 * Raises the pin that corresponds to the argument.
	 * 
	 * @param pin
	 *            the index of the pin to raise
	 * @throws IllegalArgumentException
	 *             if the argument is not between 1 and 8 inclusive
	 */
	public void raiseOnePin(int pin) {
		if (pin < 0 || pin > 7) {
			throw new IllegalArgumentException("Invalid index");
		}
		listOfPins[pin - 1] = true;
	}
	
	/**
	 * Lowers the pin that corresponds to the argument.
	 * 
	 * @param pin
	 *            the index of the pin to raise
	 * @throws IllegalArgumentException
	 *             if the argument is not between 1 and 8 inclusive
	 */
	public void lowerOnePin(int pin) {
		if (pin < 0 || pin > 7) {
			throw new IllegalArgumentException("Invalid index");
		}
		listOfPins[pin - 1] = false;
	}

	/**
	 * Lowers all the pins in the cell.
	 * <p>
	 * This is equivalent to <code> displayLetter(' ') </code>.
	 */
	public void clear() {
		Arrays.fill(listOfPins, false);
	}

	/**
	 * Gets the pin's current state (raised or lowered) at the index that corresponds to the argument.
	 * 
	 * @param index
	 * 			the index of the pin
	 * @return
	 * 			the pin's current state
	 */
	public boolean getPinState(int index) {
		return listOfPins[index];
	}

	/**
	 * Gets the braille cell's number of pins. This is to allow support for braille cells with 6 pins
	 * in the future, while currently we are using the 8 pin braille cell. 
	 * @return
	 * 			the number of pins in this BrailleCell
	 */
	public int getNumberOfPins() {
		return listOfPins.length;
	}
}