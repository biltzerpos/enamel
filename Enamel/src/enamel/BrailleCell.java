package enamel;


import java.util.HashMap;

import javax.swing.JRadioButton;

/**
 * This class implements a Braille Cell with 8 pins. The class contains methods
 * for displaying letters on the cell
 * as well as raising and/or lowering individual pins.
 * <p>
 * This class supports the standard Braille encoding of English characters plus the space character using
 * the <code> displayCharacter()</code> method. For any non-standard characters or ones
 * that aren't included by default, use the <code> setPins()</code> method,
 * which takes a string of 1s and 0s as argument and sets the pins accordingly.
 * 1 meaning raised and 0 meaning lowered.
 * 
 * The constructor of this class is not public. Objects are created when a 
 * <code> Simulator</code> object is created. Use the <code> getCell</code> method of class 
 * <code> Simulator</code> to obtain references to individual <code> BrailleCell</code> objects.
 * 
 * @author Team 4: Yassin Mohamed, Qassim Allauddin, Derek Li, Artem Solovey.
 *
 */
public class BrailleCell {

	JRadioButton radio1x1;
	JRadioButton radio1x2;
	JRadioButton radio2x1;
	JRadioButton radio2x2;
	JRadioButton radio3x1;
	JRadioButton radio3x2;
	JRadioButton radio4x1;
	JRadioButton radio4x2;
	private static HashMap<Character, String> alphabet = new HashMap<Character, String>();

	private void initializeAlphabet() {
		alphabet.put('a', "10000000");
		alphabet.put('b', "10100000");
		alphabet.put('c', "11000000");
		alphabet.put('d', "11010000");
		alphabet.put('e', "10010000");
		alphabet.put('f', "11100000");
		alphabet.put('g', "11110000");
		alphabet.put('h', "10110000");
		alphabet.put('i', "01100000");
		alphabet.put('j', "01110000");
		alphabet.put('k', "10001000");
		alphabet.put('l', "10101000");
		alphabet.put('m', "11001000");
		alphabet.put('n', "11011000");
		alphabet.put('o', "10011000");
		alphabet.put('p', "11101000");
		alphabet.put('q', "11111000");
		alphabet.put('r', "10111000");
		alphabet.put('s', "01101000");
		alphabet.put('t', "01111000");
		alphabet.put('u', "10001100");
		alphabet.put('v', "10101100");
		alphabet.put('w', "01110100");
		alphabet.put('x', "11001100");
		alphabet.put('y', "11011100");
		alphabet.put('z', "10011100");
		alphabet.put(' ', "00000000");

	}

	/**
	 * 
	 * Class constructor, initializes the class's 8 radio button variables as
	 * the ones passed into the constructor. The arguments and the class
	 * parameters have the same reference to allow the class to alter the state
	 * of the radio buttons.
	 * <p>
	 * Radio buttons can be thought of as occupying a 4x2 grid, with each button
	 * having index Row x Coloumn starting with 1x1 on the top-left.
	 * 
	 * 
	 * @param radio1x1
	 *            radio button at position 1x1
	 * @param radio1x2
	 *            radio button at position 1x2
	 * @param radio2x1
	 *            radio button at position 2x1
	 * @param radio2x2
	 *            radio button at position 2x2
	 * @param radio3x1
	 *            radio button at position 3x1
	 * @param radio3x2
	 *            radio button at position 3x2
	 * @param radio4x1
	 *            radio button at position 4x1
	 * @param radio4x2
	 *            radio button at position 4x2
	 */
	 BrailleCell(JRadioButton radio1x1, JRadioButton radio1x2, JRadioButton radio2x1, JRadioButton radio2x2,
			JRadioButton radio3x1, JRadioButton radio3x2, JRadioButton radio4x1, JRadioButton radio4x2) {

		this.initializeAlphabet();
		this.radio1x1 = radio1x1;
		this.radio1x2 = radio1x2;
		this.radio2x1 = radio2x1;
		this.radio2x2 = radio2x2;
		this.radio3x1 = radio3x1;
		this.radio3x2 = radio3x2;
		this.radio4x1 = radio4x1;
		this.radio4x2 = radio4x2;

	}

	/**
	 * 
	 * Displays the character passed as argument on this Braille Cell object in
	 * Braille.
	 * <p>
	 * The method works by selecting specific radio buttons that represent
	 * raised "pins", or the parts of the letter that would be felt by a human
	 * hand.
	 * 
	 * 
	 * @param a
	 *            the character to be displayed in Braille
	 * @throws IllegalArgumentException
	 *             if the character isn't part of the standard alphabet or a
	 *             space character.
	 */
	public void displayCharacter(char a) {

		a = Character.toLowerCase(a);
		if (!alphabet.containsKey(a)) {
			throw new IllegalArgumentException("Non standard character");
		}
		this.setPins(alphabet.get(a));

	}

	/**
	 * Takes an eight character string of 1s and 0s as argument, and sets the
	 * the 8 pins accordingly. 1 corresponds to raising the pin, 0 to lowering it.
	 * The first character corresponds to the
	 * top-left pinn, the next character corresponding to the next pin
	 * from left to right and top to bottom.
	 * 
	 * 
	 * @param pins
	 *            string of 1s and 0s that is 8 characters long that sets the
	 *            state of the eight pins that form the cell
	 * @throws IllegalArgumentException
	 *             if the string is not 8 characters long or if it contains any
	 *             character that isn't a 1 or a 0
	 */
	public void setPins(String pins) {
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

		for (int i = 0; i <= 7; i++) {
			if (pins.charAt(i) == '1') {
				raiseOnePin(i + 1);
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
		if (pin < 1 || pin > 8) {
			throw new IllegalArgumentException("Invalid index");
		}
		switch (pin) {
		case 1:
			radio1x1.setSelected(true);
			break;
		case 2:
			radio1x2.setSelected(true);
			break;
		case 3:
			radio2x1.setSelected(true);
			break;
		case 4:
			radio2x2.setSelected(true);
			break;
		case 5:
			radio3x1.setSelected(true);
			break;
		case 6:
			radio3x2.setSelected(true);
			break;
		case 7:
			radio4x1.setSelected(true);
			break;
		case 8:
			radio4x2.setSelected(true);
			break;
		default:
			break;

		}
	}

	/**
	 * Lowers the pin that corresponds to the argument.
	 * 
	 * @param pin
	 *            the index of the pin to lower
	 * @throws IllegalArgumentException
	 *             if the argument is not between 1 and 8 inclusive
	 */

	public void lowerOnePin(int pin) {
		if (pin < 1 || pin > 8) {
			throw new IllegalArgumentException("Invalid index");
		}
		switch (pin) {
		case 1:
			radio1x1.setSelected(false);
			break;
		case 2:
			radio1x2.setSelected(false);
			break;
		case 3:
			radio2x1.setSelected(false);
			break;
		case 4:
			radio2x2.setSelected(false);
			break;
		case 5:
			radio3x1.setSelected(false);
			break;
		case 6:
			radio3x2.setSelected(false);
			break;
		case 7:
			radio4x1.setSelected(false);
			break;
		case 8:
			radio4x2.setSelected(false);
			break;
		default:
			break;

		}
	}
	/**
	 * Lowers all the pins in the cell.
	 * <p>
	 * This is equivalent to <code> displayLetter(' ') </code>.
	 * 
	 * 
	 */
	public void clear() {

		radio1x1.setSelected(false);
		radio1x2.setSelected(false);
		radio2x1.setSelected(false);
		radio2x2.setSelected(false);
		radio3x1.setSelected(false);
		radio3x2.setSelected(false);
		radio4x1.setSelected(false);
		radio4x2.setSelected(false);
	}

}
