package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import authoring.GUI;
import commands.CellCharCommand;
import commands.CellLowerCommand;
import commands.CellRaiseCommand;
import commands.ClearAllCommand;
import commands.ClearCellCommand;
import commands.GoHereCommand;
import commands.PauseCommand;
import commands.RepeatButtonCommand;
import commands.RepeatCommand;
import commands.ResetButtonCommand;
import commands.SetPinsCommand;
import commands.SetStringCommand;
import commands.SetVoiceCommand;
import commands.SkipButtonCommand;
import commands.SkipCommand;
import commands.SoundCommand;
import commands.TTSCommand;
import commands.UserInputCommand;

/**
 * This class is used as an action listener whenever the "New Item" button is
 * clicked. It enables the user to set items from dialog box with their value.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 4/3/2017
 *
 */
public class NewButtonListener implements ActionListener {

	private GUI gui;

	/**
	 * Create the NewButtonListener with a reference to the base GUI object
	 * (required to access the left panel)
	 *
	 * @param gui
	 *            Instance of currently running GUI
	 */
	public NewButtonListener(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Show the Add Item dialog
		String[] possibilities = { "Pause", "Text-to-speech", "Display String", "Repeat", "Button Repeat",
				"Button Location", "User Input", "Sound", "Reset Buttons", "Go To Location", "Clear All", "Clear Cell",
				"Set Pins", "Set Character", "Raise Pin", "Lower Pin", "Set Voice", "Location Tag" };
		Object value;
		
		String answer;
		answer = (String) JOptionPane.showInputDialog(gui, "Select the type of the item.", "Add Item",
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "");
		
		processAnswer(answer);

	}

	public void processAnswer(String answer) {
		Object value;
		if (answer != null) {
			switch (answer) {
			case "Pause":
				value = JOptionPane.showInputDialog(gui, "Length of time to wait", "Edit Item Details",
					JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != ""){gui.getLeftPanel().addItem(new PauseCommand((String)value));}
				break;
			case "Text-to-speech":
				value = JOptionPane.showInputDialog(gui, "Text to say", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new TTSCommand((String) value));
				}
				break;
			case "Display String":
				value = JOptionPane.showInputDialog(gui, "String to display", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new SetStringCommand((String) value));
				}
				break;
			case "Repeat":
				value = JOptionPane.showInputDialog(gui, "Text to be repeated", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new RepeatCommand((String) value));
				}
				break;
			case "Button Repeat":
				value = JOptionPane.showInputDialog(gui, "Button to use for repeating", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new RepeatButtonCommand((String) value));
				}
				break;
			case "Button Location":
				value = JOptionPane.showInputDialog(gui, "Button and identifier (space separated)", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new SkipButtonCommand((String) value));
				}
				break;
			case "User Input":
				gui.getLeftPanel().addItem(new UserInputCommand());
				break;
			case "Sound":
				value = JOptionPane.showInputDialog(gui, "File path: ", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new SoundCommand((String) value));
				}
				break;
			case "Reset Buttons":
				gui.getLeftPanel().addItem(new ResetButtonCommand(""));
				break;
			case "Go To Location":
				value = JOptionPane.showInputDialog(gui, "Enter location to go to", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new SkipCommand((String) value));
				}
				break;
			case "Clear All":
				gui.getLeftPanel().addItem(new ClearAllCommand(""));
				break;
			case "Clear Cell":
				value = JOptionPane.showInputDialog(gui, "Cell number", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new ClearCellCommand((String) value));
				}
				break;
			case "Set Pins":
				value = JOptionPane.showInputDialog(gui, "Cell and pins (space separated)", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new SetPinsCommand((String) value));
				}
				break;
			case "Set Character":
				value = JOptionPane.showInputDialog(gui, "Cell and character (space seperated)", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new CellCharCommand((String) value));
				}
				break;
			case "Raise Pin":
				value = JOptionPane.showInputDialog(gui, "Cell and Pin to raise (space separated)", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new CellRaiseCommand((String) value));
				}
				break;
			case "Lower Pin":
				value = JOptionPane.showInputDialog(gui, "Cell and Pin to lower (space separated)", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new CellLowerCommand((String) value));
				}
				break;
			case "Set Voice":
				value = JOptionPane.showInputDialog(gui, "Enter a voice number", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new SetVoiceCommand((String) value));
				}
				break;
			case "Location Tag":
				value = JOptionPane.showInputDialog(gui, "Enter name of location", "Edit Item Details",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (value != null && value != "") {
					gui.getLeftPanel().addItem(new GoHereCommand((String) value));
				}
				break;
			default:
				break;
			}
		}
	}

}
