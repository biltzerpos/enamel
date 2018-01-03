package commands;

/**
 * Command wrapper to represent the /~disp-cellAll command in the player. No
 * values are required for this command, and any given are ignored.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class ClearAllCommand extends PlayerCommand {

	/**
	 * Construct a new instance of the Clear All command
	 *
	 * @param args
	 *            Ignored argument
	 */
	public ClearAllCommand(String args) {
	}

	@Override
	public String toString() {
		return "Clear All Braille Cells";
	}

	@Override
	public String serialize() {
		return "/~disp-clearAll";
	}

	@Override
	public String getEditLabel() {
		return "Ignored";
	}

	@Override
	public String getCurrentValue() {
		return "";
	}

	@Override
	public void setCurrentValue(String val) {
	}

}
