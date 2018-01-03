package commands;

/**
 * Command wrapper to represent the /~disp-clear-cell command in the player.
 * Values consist of a string consisting of only the cell number that should be
 * cleared (e.g. "5")
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class ClearCellCommand extends PlayerCommand {

	private String cellNumber = "";

	/**
	 * Construct a new instance with the value set to the given value
	 *
	 * @param cellNumber
	 *            Desired value of the ClearCellCommand
	 */
	public ClearCellCommand(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	@Override
	public String toString() {
		return "Clear Braille Cell " + cellNumber;
	}

	@Override
	public String serialize() {
		return "/~disp-clear-cell:" + cellNumber;
	}

	@Override
	public String getEditLabel() {
		return "Cell number";
	}

	@Override
	public String getCurrentValue() {
		return cellNumber;
	}

	@Override
	public void setCurrentValue(String cellNumber) {
		this.cellNumber = cellNumber;
	}

}
