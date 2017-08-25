package commands;

/**
 * Command wrapper to represent the /~user-input command in the player. No
 * values are used and any provided are ignored.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class UserInputCommand extends PlayerCommand {

	@Override
	public String toString() {
		return "Wait for User Input";
	}

	@Override
	public String serialize() {
		return "/~user-input";
	}

	@Override
	public String getEditLabel() {
		return "Ignored";
	}

	@Override
	public String getCurrentValue() {
		return null;
	}

	@Override
	public void setCurrentValue(String val) {
	}

}
