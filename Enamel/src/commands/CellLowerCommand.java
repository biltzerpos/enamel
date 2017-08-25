package commands;

/**
 * Command wrapper to represent the /~disp-cell-lower command in the player.
 * Values consist of a string with a space separator between a cell number and a
 * single number pin to lower (e.g. "4 1")
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class CellLowerCommand extends PlayerCommand {

	private String cellAndPin = "";

	/**
	 * Construct a new instance with the value set to the given value
	 *
	 * @param cellAndPin
	 *            Desired value of the CellLowerCommand
	 */
	public CellLowerCommand(String cellAndPin) {
		this.cellAndPin = cellAndPin;
	}

	@Override
	public String toString() {
		return "Braille Cell Lower Pin Number: " + cellAndPin;
	}

	@Override
	public String serialize() {
		return "/~disp-cell-lower:" + cellAndPin;
	}

	@Override
	public String getEditLabel() {
		return "Cell and Pin to lower (space separated)";
	}

	@Override
	public String getCurrentValue() {
		return cellAndPin;
	}

	@Override
	public void setCurrentValue(String cellAndPin) {
		this.cellAndPin = cellAndPin;
	}

}
