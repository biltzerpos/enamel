package commands;

/**
 * Command wrapper to represent the /~repeat command in the player. Values
 * consist of any arbitrary string which is text that should be read. The values
 * are automatically wrapped in the correct /~repeat and /~endrepeat tags when
 * serialized.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class RepeatCommand extends PlayerCommand {

	private String repeatedText;

	/**
	 * Construct a RepeatCommand with the repeated text value set to the input
	 * value
	 * 
	 * @param repeatedText
	 *            Desired value of the repeated text
	 */
	public RepeatCommand(String repeatedText) {
		this.repeatedText = repeatedText;
	}

	@Override
	public String toString() {
		return "Text to be Repeat: " + repeatedText;
	}

	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder();
		sb.append("/~repeat\n");
		sb.append(repeatedText.trim() + "\n");
		sb.append("/~endrepeat");

		return sb.toString();
	}

	@Override
	public String getEditLabel() {
		return "Text to be repeated";
	}

	@Override
	public String getCurrentValue() {
		return repeatedText.trim();
	}

	@Override
	public void setCurrentValue(String repeatedText) {
		this.repeatedText = repeatedText;
	}

}
