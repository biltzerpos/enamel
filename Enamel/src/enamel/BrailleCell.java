package enamel;

import java.util.HashMap;
import java.util.*;

/**
 * This class implements a Braille Cell with 8 pins. The class contains methods
 * for displaying letters on the cell as well as raising and/or lowering
 * individual pins.
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
 *
 */
public class BrailleCell {

	private boolean[] listOfPins = new boolean[8];
	private static HashMap<Character, String> alphabet = new HashMap<Character, String>();

	
	public BrailleCell() {
		this.initializeAlphabet();
	}
	
	private void initializeAlphabet() {
		// new alphabet - matches the hardware B11 braille cell layout
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

	public void displayCharacter(char a) throws InterruptedException {

		a = Character.toLowerCase(a);
		if (!alphabet.containsKey(a)) {
			throw new IllegalArgumentException("Non standard character");
		}
		this.setPins(alphabet.get(a)); // GUI's set pins. Once we don't need GUI
										// anymore, remove it.
	}

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

	public void raiseOnePin(int pin) {
		if (pin < 0 || pin > 7) {
			throw new IllegalArgumentException("Invalid index");
		}
		listOfPins[pin - 1] = true;
	}

	public void lowerOnePin(int pin) {
		if (pin < 0 || pin > 7) {
			throw new IllegalArgumentException("Invalid index");
		}
		listOfPins[pin - 1] = false;
	}

	public void raiseMultiplePins(int[] pins) {
		for (int i = 0; i < pins.length; i++) {
		}
		for (int s : pins) {
			listOfPins[s - 1] = true;
		}
	}

	public void lowerMultiplePins(int[] pins) {
		for (int s : pins) {
			listOfPins[s - 1] = false;
		}
	}

	public String speakPinState(int pins) {
		if (listOfPins[pins] == true) {
			return ("Pin " + (pins + 1) + "is up");
		} else {
			return ("Pin " + (pins + 1) + "is down");
		}
	}

	public void clear() {
		Arrays.fill(listOfPins, false);
	}

	public boolean getPinState(int index) {
		return listOfPins[index];
	}

	public int getNumberOfPins() {
		return listOfPins.length;
	}
}