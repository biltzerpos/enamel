package commands;

/**
 * Abstract class which represents commands that may be issued to the Player.
 * All commands must implement this interface. The behavior of the commands
 * (specifically their serialization) is defined by the appropriate section of
 * the Player documentation
 * 
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
abstract public class PlayerCommand {
	@Override
	abstract public String toString();

	/**
	 * Serialize the command into a format that can be written to file, and
	 * later read into the player.
	 *
	 * @return String representation of the current command
	 */
	abstract public String serialize();

	/**
	 * Get a short prompt for the value of the command. Used when editing a
	 * command on the command line
	 *
	 * @return Short string describing what the authoring user should enter
	 */
	abstract public String getEditLabel();

	/**
	 * Obtain the current internal representation (if any extra exists). Used
	 * for most implementing classes to return argument information.
	 *
	 * @return String consisting of any internal information required
	 */
	abstract public String getCurrentValue();

	/**
	 * Set the internal extra information for the command. For many classes this
	 * is the required argument information.
	 *
	 * @param newValue
	 *            The new value of the representation that the command should
	 *            take.
	 */
	abstract public void setCurrentValue(String newValue);
}
