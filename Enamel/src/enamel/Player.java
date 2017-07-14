package enamel;

import java.util.LinkedList;

public abstract class Player {

	int brailleCellNumber;
	int ButtonNumber;
	LinkedList<BrailleCell> brailleList = new LinkedList<BrailleCell>();
	
	public Player(int brailleCellNumber, int ButtonNumber) {

		if (brailleCellNumber <= 0 || ButtonNumber <= 0)
			throw new IllegalArgumentException("Non-positive integer entered.");

		this.brailleCellNumber = brailleCellNumber;
		this.ButtonNumber = ButtonNumber;

		System.out.println(brailleCellNumber + " " + ButtonNumber);

		BrailleCell cell = new BrailleCell();

		brailleList.add(cell);
	}

	/**
	 * Returns a reference to the BrailleCell object at the index passed as
	 * argument. Braille Cells are numbered left to right and top to bottom as
	 * they appear in the frame, from 0 to (brailleCellNumber - 1),
	 * brailleCellNumber being the number of BrailleCell objects initialized by
	 * the constructor.
	 * 
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
	 * effectively making them display nothing.
	 */
	public void clearAllCells() {
		for (int i = 0; i < this.brailleCellNumber; i++) {
			this.brailleList.get(i).clear();
		}
	}

	/**
	 * Displays the string passed as argument on all the Braille Cells If the
	 * string is shorter than the total number of Braille Cells, the remaining
	 * cells are cleared. However, if the string is longer it only displays the
	 * part of it up to however many Braille Cells there are and ignores the
	 * rest.
	 * 
	 * @param aString
	 *            the String to be displayed on the Braille Cells
	 */
	public void displayString(String aString) throws InterruptedException {
		this.clearAllCells();
		for (int i = 0; i < this.brailleCellNumber && i < aString.length(); i++) {
			this.brailleList.get(i).displayCharacter(aString.charAt(i));
		}
	}

	public abstract void refresh();

	public abstract void addSkipButtonListener(int index, String param, ScenarioParser sp);

	public abstract void removeButtonListener(int index);

	public abstract void addRepeatButtonListener(int index, ScenarioParser sp);
}
