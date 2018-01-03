package commands;

/**
 * Command wrapper to represent the /~skip-button command in the player. Values
 * are given as a button, followed by the target jump that should be sought to.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class SkipButtonCommand extends PlayerCommand {

	private String numAndIdentifier = "";

	/**
	 * Constructor for SkipButtonCommand which presets the internal value to a
	 * given string
	 *
	 * @param numAndIdentifier
	 *            Number and identifier for the skip button
	 */
	public SkipButtonCommand(String numAndIdentifier) {
		this.numAndIdentifier = numAndIdentifier;
	}

	@Override
	public String toString() {
		return "Button Location Target: " + numAndIdentifier;
	}

	@Override
	public String serialize() {
		return "/~skip-button:" + numAndIdentifier;
	}

	@Override
	public String getEditLabel() {
		return "Button and identifier (space separated)";
	}

	@Override
	public String getCurrentValue() {
		return numAndIdentifier;
	}

	@Override
	public void setCurrentValue(String numAndIdentifier) {
		this.numAndIdentifier = numAndIdentifier;
	}

}
