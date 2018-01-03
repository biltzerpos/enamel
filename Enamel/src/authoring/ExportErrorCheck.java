package authoring;

import java.util.HashMap;
import java.util.List;

import commands.CellCharCommand;
import commands.CellLowerCommand;
import commands.CellRaiseCommand;
import commands.ClearCellCommand;
import commands.GoHereCommand;
import commands.PlayerCommand;
import commands.RepeatButtonCommand;
import commands.SetPinsCommand;
import commands.SetStringCommand;
import commands.SkipButtonCommand;
import commands.SkipCommand;
import commands.UserInputCommand;

/**
 * Class to handle simple error checking in a scenario file before it is
 * exported Checks for things like missing jump tags, or calling buttons that
 * have not been initialized
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 *
 */
public class ExportErrorCheck {

	/**
	 * Method to check whether a scenario has jump events to jump tags that
	 * don't exist
	 *
	 * @param commands
	 *            list of commands that make up the scenario
	 * @return true if every jump event has a jump tag false otherwise
	 */
	public static boolean checkJumpTags(List<PlayerCommand> commands) {

		// Hashmap to keep track of jump tags being used
		HashMap<String, Integer> tagTracker = new HashMap<String, Integer>();
		int tagCount;

		// iterate over commands to get all skip and skip buttons commands
		for (PlayerCommand pc : commands) {
			// if a skip command, put tag in tagTracker with value 0
			if (pc instanceof SkipCommand) {
				tagTracker.put(pc.getCurrentValue(), 0);
			}
			// if a skip button command, put tag in tagTracker with value 0
			if (pc instanceof SkipButtonCommand) {
				tagTracker.put(pc.getCurrentValue().split(" ", 2)[1], 0);
			}
		}

		// iterate over commands to match tagTracker with jump tags present
		for (PlayerCommand pc : commands) {

			// if a skip tag command, increase the value of tag n tagTracker by
			// 1
			if (pc instanceof GoHereCommand) {
				if (tagTracker.containsKey(pc.getCurrentValue())) {
					tagCount = tagTracker.get(pc.getCurrentValue()) + 1;
					tagTracker.put(pc.getCurrentValue(), tagCount);
				}
			}
		}

		// if there are any tags in tagTracker with value 0, it means there was
		// a jump without a matching tag
		return !tagTracker.containsValue(0);
	}

	/**
	 * Method to check whether a scenario calls a button that hasn't been
	 * initialized
	 *
	 * @param commands
	 *            list of commands that make up the scenario
	 * @param numButtons
	 *            number of initialized buttons
	 * @return true if every button call was to a valid button false otherwise
	 */
	public static boolean checkButtonNumber(List<PlayerCommand> commands, int numButtons) {
		boolean errorFree = true;

		// iterate over command to find RepeatButtonCommands and
		// SkipButtonCommands
		for (PlayerCommand pc : commands) {
			// if command is a RepeatButtonCommand
			if (pc instanceof RepeatButtonCommand) {

				// if it calls for a button that doesn't exist
				if (Integer.parseInt(pc.getCurrentValue()) >= numButtons
						|| Integer.parseInt(pc.getCurrentValue()) < 0) {
					errorFree = false;
					break;
				}
			}

			// if command is a SkipButtonCommand
			if (pc instanceof SkipButtonCommand) {
				// If it calls for a button that doesn't exist
				if (Integer.parseInt(pc.getCurrentValue().split(" ")[0]) >= numButtons
						|| Integer.parseInt(pc.getCurrentValue().split(" ")[0]) < 0) {
					// There's an error, break and return false
					errorFree = false;
					break;

				}
			}

		}
		return errorFree;
	}

	/**
	 * Method to check whether a scenario tries to call on a braille cell that
	 * doesn't exist
	 *
	 * @param commands
	 *            list of commands that make up the scenario
	 * @param numCells
	 *            number of initialized cells
	 * @return true if no commands call for cells that don't exist false
	 *         otherwise
	 */
	public static boolean checkCellNumber(List<PlayerCommand> commands, int numCells) {

		boolean errorFree = true;

		// Iterate over all commands
		for (PlayerCommand pc : commands) {
			// Check if these commands call a cell that doesn't exist
			if (pc instanceof CellCharCommand || pc instanceof CellLowerCommand || pc instanceof CellRaiseCommand
					|| pc instanceof SetPinsCommand || pc instanceof ClearCellCommand) {
				// If they access a cell that doesn't exist, break and return
				// false
				if (Integer.parseInt(pc.getCurrentValue().split("")[0]) >= numCells
						|| Integer.parseInt(pc.getCurrentValue().split("")[0]) < 0) {
					errorFree = false;
					break;
				}
			}

			// If a command tries to display a string that's too long, break and
			// return false
			if (pc instanceof SetStringCommand) {
				if (pc.getCurrentValue().length() > numCells) {
					errorFree = false;
					break;
				}
			}
		}
		return errorFree;
	}

	/**
	 * Method to check whether or not all UserInput commands have a button setup
	 * command before them
	 *
	 * @param commands
	 *            list of commands that make up the scenario
	 * @return true if all UserInput commands have button setup commands before
	 *         them false otherwise
	 */
	public static boolean checkUserInput(List<PlayerCommand> commands) {
		boolean seenUserInput = false;
		// whether a buttons are set up before a UserInput command
		boolean setUp = true;

		// iterate backwards over commands
		for (int i = commands.size() - 1; i >= 0; i--) {
			// If a UserInput command has not been seen yet, iterate until one
			// is found
			if (!seenUserInput) {
				// If this command is a user input command
				if (commands.get(i) instanceof UserInputCommand) {
					seenUserInput = true;
					setUp = false;
				}
			}
			// if a UserInput command has been seen, iterate until a SkipButton
			// or RepeatButton command is seen
			// then iterate until the next UserInput command is seen
			else {
				if (commands.get(i) instanceof SkipButtonCommand || commands.get(i) instanceof RepeatButtonCommand) {
					seenUserInput = false;
					setUp = true;
				}
			}
		}

		return setUp;
	}
}
