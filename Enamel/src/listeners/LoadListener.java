package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import authoring.ColourMapper;
import authoring.GUI;
import commands.CellCharCommand;
import commands.CellLowerCommand;
import commands.CellRaiseCommand;
import commands.ClearAllCommand;
import commands.ClearCellCommand;
import commands.GoHereCommand;
import commands.PauseCommand;
import commands.PlayerCommand;
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
 * This class is used as an action listener whenever the "Import" button is
 * clicked. It serves as a way to initialize the application. Allowing the user
 * to set up saved scenario.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-02
 */
public class LoadListener implements ActionListener {

	private GUI gui;
	private ColourMapper mapper;

	/**
	 * Create an import listener with a reference to the parent GUI.
	 *
	 * @param gui
	 *            A reference to the parent GUI, needed in order to properly
	 *            access the command list
	 * @param mapper
	 *            Shared reference to the color mapper
	 */
	public LoadListener(GUI gui, ColourMapper mapper) {
		this.gui = gui;
		this.mapper = mapper;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<String> inputLines = getInput();

		// Process first three lines
		int i = 0;
		for (String line : inputLines) {
			if (line.isEmpty()) {
				continue;
			}

			if (i >= 3) {
				break;
			}
			String components[] = line.split(" ", 2);
			String firstWord = components[0];
			String restOfLine = "";
			if (components.length > 1) {
				restOfLine = components[1];
			}

			if (firstWord.compareTo("Cell") == 0) {
				gui.getSettingsPanel().setCellField(restOfLine);
			} else if (firstWord.compareTo("Button") == 0) {
				gui.getSettingsPanel().setButtonFieldText(restOfLine);
			} else {
				gui.getSettingsPanel().setTitleField(line);
			}
			i++;
		}

		List<PlayerCommand> allCommands = parseString(inputLines);
		mapper.addColourMapping(allCommands);
		for (PlayerCommand pc : allCommands) {
			gui.getLeftPanel().addItem(pc);
		}
	}

	private List<String> getInput() {
		JFileChooser importDialog = new JFileChooser();
		List<String> str = new ArrayList<String>();

		FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("text files (*.txt)", "txt");
		importDialog.addChoosableFileFilter(txtFilter);
		importDialog.setFileFilter(txtFilter);

		int r = importDialog.showOpenDialog(gui);
		if (r == JFileChooser.APPROVE_OPTION) {
			URI uri = importDialog.getSelectedFile().toURI();
			gui.getRightPanel().setStart(true);
			gui.getRightPanel().setNew(true);
			gui.getRightPanel().setExport(true);
			gui.getRightPanel().setReadFile(true);
			gui.getRightPanel().setNewQuestion(true);
			try {
				str = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
			} catch (IOException e) {
				// An IOException occured, this is outside the application's
				// control.
				// Gracefully return nothing
				return new ArrayList<String>();
			}
		}
		return str;
	}

	/**
	 * Parse a list of string lines (e.g. lines from a file) into serialized
	 * strings. Made private once testing is completed.
	 *
	 * @param inputLines
	 *            List of strings, each one being a line to be processed
	 *
	 * @return Generic ordered list of the processed commands
	 */
	public List<PlayerCommand> parseString(List<String> inputLines) {
		List<PlayerCommand> result = new ArrayList<>();
		boolean inCommand = false;
		StringBuilder repeatHolder = new StringBuilder("");
		int i = 0;

		for (String line : inputLines) {
			if (line.isEmpty()) {
				continue;
			}

			// Ignore first three lines
			if (i < 3) {
				i++;
				continue;
			}

			// When a repeat is seen, begin tracking the contents
			if (line.compareTo("/~repeat") == 0) {
				inCommand = true;
				repeatHolder = new StringBuilder("");
				continue;
			}

			// Track the repeat contents until the end is seen
			if (inCommand && line.compareTo("/~endrepeat") != 0) {
				repeatHolder.append(line.trim() + "\n");
				continue;
			}

			// Put the entire contents of the end into the command and resume
			// normal mode
			if (line.compareTo("/~endrepeat") == 0) {
				inCommand = false;
				PlayerCommand pc = new RepeatCommand(repeatHolder.toString());
				result.add(pc);
				continue;
			}

			// Check for TTS, it has no header
			if (line.length() < 2 || line.substring(0, 2).compareTo("/~") != 0) {
				result.add(new TTSCommand(line));
				continue;
			}

			// If here, all valid commands will have at least length 2 (/~)
			String components[] = line.substring(2).split(":", 2);
			String command = components[0];
			String args;

			if (components.length == 2) {
				args = components[1].trim();
			} else {
				args = "";
			}

			PlayerCommand pc;

			switch (command) {
			case "pause":
				pc = new PauseCommand(args);
				break;
			case "set-voice":
				pc = new SetVoiceCommand(args);
				break;
			case "disp-string":
				pc = new SetStringCommand(args);
				break;
			case "repeat-button":
				pc = new RepeatButtonCommand(args);
				break;
			case "skip-button":
				pc = new SkipButtonCommand(args);
				break;
			case "user-input":
				pc = new UserInputCommand();
				break;
			case "sound":
				pc = new SoundCommand(args);
				break;
			case "reset-buttons":
				pc = new ResetButtonCommand(args);
				break;
			case "skip":
				pc = new SkipCommand(args);
				break;
			case "disp-clearAll":
				pc = new ClearAllCommand(args);
				break;
			case "disp-clear-cell":
				pc = new ClearCellCommand(args);
				break;
			case "disp-cell-pins":
				pc = new SetPinsCommand(args);
				break;
			case "disp-cell-char":
				pc = new CellCharCommand(args);
				break;
			case "disp-cell-raise":
				pc = new CellRaiseCommand(args);
				break;
			case "disp-cell-lower":
				pc = new CellLowerCommand(args);
				break;
			default:
				pc = new GoHereCommand(line.substring(2));
				break;
			}

			result.add(pc);
		}

		return result;
	}

}
