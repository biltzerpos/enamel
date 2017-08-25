package authoring;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import commands.GoHereCommand;
import commands.PlayerCommand;
import commands.SkipButtonCommand;
import commands.SkipCommand;

/**
 * This a utility class is used to create a mapping between location tags in the
 * scenario and their text colour. There method cycles between 11 set colours to
 * use in the mappings. The goal is to make sure all commands that use the same
 * location tag are coloured the same.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 *
 */
public class ColourMapper {
	/** Mapper from location tags to colors. Declared public for testing purposes */
	public HashMap<String, Color> colourMap;
	// List of colors to cycle through
	private final Color[] COLOURCYCLE = { Color.blue, Color.cyan, Color.red, Color.green, Color.magenta, Color.orange,
			Color.yellow, Color.gray, Color.lightGray, Color.pink, Color.darkGray, };

	/**
	 * Constructor to initialize colourMap
	 */
	public ColourMapper() {
		this.colourMap = new HashMap<String, Color>();
	}

	/**
	 * This method iterates over a list of commands, and if it sees any location
	 * tags that aren't in colourMap, it will add a new colour mapping for it.
	 *
	 * @param commands
	 *            list of commands from the scenario
	 */
	public void addColourMapping(List<PlayerCommand> commands) {
		String locationTag = "";
		// variables to iterate over COLOURCYCLE with
		int iterator = 0;
		int cycleSize = COLOURCYCLE.length;

		// Iterate over the list of player commands
		for (PlayerCommand pc : commands) {
			// If the command is of a type has just a location tag as its
			// description
			if (pc instanceof SkipCommand || pc instanceof GoHereCommand) {
				locationTag = pc.getCurrentValue();
				// If the location tag isn't mapped, add a mapping
				if (!colourMap.containsKey(locationTag)) {
					colourMap.put(locationTag, COLOURCYCLE[iterator % cycleSize]);
					iterator++;
				}
			}
			// If the command is of type SkipButton, that has a button and
			// location tag in its description
			if (pc instanceof SkipButtonCommand) {
				locationTag = pc.getCurrentValue().split(" ", 2)[1];
				// If the location tag isn't mapped, add a mapping
				if (!colourMap.containsKey(locationTag)) {
					colourMap.put(locationTag, COLOURCYCLE[iterator % cycleSize]);
					iterator++;
				}
			}
		}
	}

	/**
	 * This method returns the colour mapping for a given location tag.
	 *
	 * @param locationTag
	 *            the location tag in the event's description
	 * @return the colour mapped to that location tag
	 */
	public Color getColour(String locationTag) {
		if (!colourMap.containsKey(locationTag)) {
			return Color.BLACK;
		}

		return colourMap.get(locationTag);
	}

}
