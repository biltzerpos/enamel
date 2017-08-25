package commands;

/**
 * Command wrapper to represent the /~skip command in the player. Values are
 * given as a String containing the target jump that should be sought to.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class SkipCommand extends PlayerCommand {

	private String skipTo = "";

	/**
	 * Constructor of the SkipCommand which sets the target jump location
	 *
	 * @param skipTo
	 *            String representing the jump target
	 */
	public SkipCommand(String skipTo) {
		this.skipTo = skipTo;
	}

	@Override
	public String toString() {
		return "Go to location: " + skipTo;
	}

	@Override
	public String serialize() {
		return "/~skip:" + skipTo;
	}

	@Override
	public String getEditLabel() {
		return "Enter location to go to";
	}

	@Override
	public String getCurrentValue() {
		return skipTo;
	}

	@Override
	public void setCurrentValue(String val) {
		this.skipTo = val;
	}

}
