package commands;

/**
 * Command wrapper to represent the /~disp-cell-char command in the player.
 * Values consist of a string with a space separator between a cell number and a
 * single character (e.g. "4 A")
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class CellCharCommand extends PlayerCommand {

	private String cellAndChar = "";

	/**
	 * Construct a new cell char command with a given value
	 *
	 * @param cellAndChar
	 *            String to set the internal value to
	 */
	public CellCharCommand(String cellAndChar) {
		this.cellAndChar = cellAndChar;
	}

	@Override
	public String toString() {
		return "Braille Cell and Character: " + cellAndChar;
	}

	@Override
	public String serialize() {
		return "/~disp-cell-char:" + cellAndChar;
	}

	@Override
	public String getEditLabel() {
		return "Cell and character (space separated)";
	}

	@Override
	public String getCurrentValue() {
		return cellAndChar;
	}

	@Override
	public void setCurrentValue(String cellAndChar) {
		this.cellAndChar = cellAndChar;
	}

}
