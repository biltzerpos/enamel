package commands;

/**
 * Command wrapper to represent the /~sound command in the player. Values are
 * given as a full path to the file to be played.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class SoundCommand extends PlayerCommand {

	private String file = "";

	/**
	 * Create a new instance with the file path pre-set
	 *
	 * @param file
	 *            Path to the file to play
	 */
	public SoundCommand(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Play Sound: " + file;
	}

	@Override
	public String serialize() {
		return "/~sound:" + file;
	}

	@Override
	public String getEditLabel() {
		return "File path: ";
	}

	@Override
	public String getCurrentValue() {
		return file;
	}

	@Override
	public void setCurrentValue(String file) {
		this.file = file;
	}

}
